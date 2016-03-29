package server;

import java.io.IOException;
import java.net.ServerSocket;

import reader.CSVDataReader;
import reader.DataReader;
import reader.ReaderException;

public class PersonStoreSocketServer implements AutoCloseable {

	// server-related instance variables:
	private ServerSocket serverSocket;
	private DataReader store;
	private SocketSession connection = null;
	private boolean running = false;
	
	// constructor:
	public PersonStoreSocketServer(int port, String csvFilePath) throws IOException, ReaderException {
		if (csvFilePath == null)
			throw new NullPointerException("Parameter 'csvFilePath' cannot be null when instantiating a server.PersonStoreSocketServer");
		store = new CSVDataReader(csvFilePath);
		serverSocket = new ServerSocket(port);
		System.err.println("PersonStoreSocketServer established at port " + serverSocket.getLocalPort());
		System.err.println("\tCSV-file path: " + ((CSVDataReader)store).getCsvFilePath());
	}
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	
	public DataReader getStore() {
		return store;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public SocketSession getConnection() {
		return connection;
	}
	
	// engine:
	public void start() {
		running = true;
		while (running) {
			try (SocketSession connection = new SocketSession(this)) {
				this.connection = connection;  // let it be instance-level
				this.connection.start();
			} catch (ReaderException | IOException e) {
				running = false;
				System.err.println("An Exception occurred while running server:");
				e.printStackTrace();
			} finally {
				this.connection = null;  // clear it from instance scope
			}
		}
	}
	
	public void stop() {
		running = false;
	}
	
	// closer:
	@Override
	public void close() throws IOException{
		serverSocket.close();
		System.err.println("ServerSocket at port " + serverSocket.getLocalPort() + " closed");
	}
	
	public static void setupAt(int portNumber, String csvFilePath) throws IOException, ReaderException {
		try (PersonStoreSocketServer server = new PersonStoreSocketServer(portNumber, csvFilePath)) {
			server.start();
		}
	}
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Class server.PersonStoreSocketServer ha to be run with 2 command-line arguments," +
					" CSV-file path and port number, respectively");
			return;
		}
		String csvFilePath;
		int portNumber = 0;
		try {
			csvFilePath = args[0];
			portNumber = Integer.valueOf(args[1]);
			System.err.println("Setting up server...");
			setupAt(portNumber, csvFilePath);
		} catch (NumberFormatException | ReaderException | IOException e) {
			System.err.println("An Exception occurred before/while setting up server" +
					(portNumber != 0 ? (" at port " + portNumber) : "") + ":");
			e.printStackTrace();
		}
	}
	
}

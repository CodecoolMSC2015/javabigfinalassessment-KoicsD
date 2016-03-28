package server;

import java.io.IOException;
import java.net.ServerSocket;

import reader.CSVDataReader;
import reader.DataReader;
import reader.ReaderException;
import tools.ConnectionParameters;

public class PersonStoreSocketServer implements AutoCloseable {
	
	// some settings as static constants:
	private static final int PORT_NUMBER = ConnectionParameters.getPortNumber();
	private static final String CSV_FILE_PATH = "ServerData/persons.csv";

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
		System.err.println("server.PersonStoreSocketServer established at port " + serverSocket.getLocalPort());
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
			} catch (IOException | ReaderException e) {
				running = false;
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
		try {
			setupAt(PORT_NUMBER, CSV_FILE_PATH);
		} catch (IOException | ReaderException e) {
			e.printStackTrace();
		}
	}
	
}

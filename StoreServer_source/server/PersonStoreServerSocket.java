package server;

import java.io.IOException;
import java.net.ServerSocket;

import connection.ConnectionParameters;

public class PersonStoreServerSocket implements AutoCloseable {
	
	// some settings as static constants:
	private static final int PORT_NUMBER = ConnectionParameters.getPortNumber();
	private static final String CSV_FILE_PATH = "ServerData/persons.csv";

	// server-related instance variables:
	private ServerSocket serverSocket;
	private DataReader store;
	private StoreServerClientConnection connection = null;
	private boolean running = false;
	
	// constructor:
	public PersonStoreServerSocket(int port, String csvFilePath) throws IOException {
		serverSocket = new ServerSocket(port);
		store = new CSVDataReader(csvFilePath);
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
	
	public StoreServerClientConnection getConnection() {
		return connection;
	}
	
	// engine:
	public void start() {
		running = true;
		while (running) {
			try (StoreServerClientConnection connection = new StoreServerClientConnection(this)) {
				this.connection = connection;  // let it be instance-level
				this.connection.start();
			} catch (IOException e) {
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
	}
	
	public static void setupAt(int portNumber, String csvFilePath) throws IOException {
		try (PersonStoreServerSocket server = new PersonStoreServerSocket(portNumber, csvFilePath)) {
			server.start();
		}
	}
	
	public static void main(String[] args) {
		try {
			setupAt(PORT_NUMBER, CSV_FILE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

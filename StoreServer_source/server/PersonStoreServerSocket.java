package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import connection.ConnectionParameters;

public class PersonStoreServerSocket implements AutoCloseable {
	
	// some settings as static constants:
	private static final String CSV_FILE_PATH = "persons.csv";

	// instance variables:
	private ServerSocket serverSocket;
	private DataReader store;
	
	// constructor:
	public PersonStoreServerSocket(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		store = new CSVDataReader(CSV_FILE_PATH);
	}
	
	// engine:
	public void start() {
		try (Socket socket = serverSocket.accept()) {
			while (true) {
				receive();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	// receiver:
	private void receive() {
		// TODO receiver code comes here
	}
	
	// sender:
	private void send(){
		// TODO sender code comes here
	}
	
	// closer:
	@Override
	public void close() throws IOException{
		serverSocket.close();
	}
	
	public static void main(String[] args) {
		try (PersonStoreServerSocket server = new PersonStoreServerSocket(ConnectionParameters.getPortNumber())) {
			server.start();
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

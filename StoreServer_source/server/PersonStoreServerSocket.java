package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import connection.ConnectionParameters;

public class PersonStoreServerSocket implements AutoCloseable {
	
	// some settings as static constants:
	private static final String CSV_FILE_PATH = "persons.csv";

	// server-related instance variables:
	private ServerSocket serverSocket;
	private DataReader store;
	
	// client-related instance variables: -- how about moving them to a separate class?
	private Socket socket = null;
	private ObjectOutputStream oOS = null;
	private ObjectInputStream oIS = null;
	
	// constructor:
	public PersonStoreServerSocket(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		store = new CSVDataReader(CSV_FILE_PATH);
	}
	
	// engine:
	public void start() {
		try (	Socket socket = serverSocket.accept();
				ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream())) {
			this.socket = socket;
			this.oOS = oOS;
			this.oIS = oIS;
			while (true) {
				receive();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.oIS = null;
			this.oOS = null;
			this.socket = null;
		}
	}
	
	// receiver:
	private void receive() {
		// TODO receiver code comes here
	}
	
	// sender:
	private void send() {
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

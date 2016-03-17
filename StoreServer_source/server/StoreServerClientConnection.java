package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import searching.SearchType;

public class StoreServerClientConnection implements AutoCloseable {

	// instance variables:
	private DataReader store;
	
	private Socket socket;
	private ObjectOutputStream oOS = null;
	private ObjectInputStream oIS = null;
	
	private boolean running = false;
	private String searchCriteria = null;
	private SearchType searchType = null;
	
	// constructor:
	public StoreServerClientConnection(PersonStoreServerSocket parent) throws IOException {
		socket = parent.getServerSocket().accept();
		store = parent.getStore();
	}
	
	// getters:
	public Socket getServerSocket() {
		return socket;
	}
	
	public DataReader getStore() {
		return store;
	}
	
	public boolean isRunning() {
		return running;
	}

	// engine:
	public void start() {
		try (	ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());  // for auto close
				ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream())) {
			this.oOS = oOS;  // make them instance-level
			this.oIS = oIS;
			running = true;
			while (running) {
				receive();
			}
		} catch (EOFException e) {
			// ObjectOutputStream closed on the other side, nothing to do.
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			running = false;
			this.oIS = null;  // clear them from instance-scope
			this.oOS = null;
		}
	}
	
	// receiver:
	private void receive() throws IOException {
		try {
			Object objectReceived = oIS.readObject();
			if (objectReceived instanceof String && searchCriteria == null) {
				searchCriteria = (String)objectReceived;
				if (searchType != null)
					sendData();
			} else if (objectReceived instanceof SearchType && searchType == null) {
				searchType = (SearchType)objectReceived;
				if (searchCriteria != null)
					sendData();
			} else {
				sendNull();
			}
		} catch (ClassNotFoundException e) {
			sendNull();
			e.printStackTrace();
		}
	}
	
	// sender:
	private void sendData() throws IOException {
		store.setSearchCriteria(searchCriteria);
		store.setSearchType(searchType);
		oOS.writeObject(store.getPersons());
		searchCriteria = null;
		searchType = null;
	}
	
	// handler for wrong request:
	private void sendNull() throws IOException {
		searchCriteria = null;
		searchType = null;
		oOS.writeObject(null);
	}
	
	// stopper:
	public void stop() {
		running = false;
	}
	
	// closer:
	@Override
	public void close() throws IOException {
		socket.close();
	}

}

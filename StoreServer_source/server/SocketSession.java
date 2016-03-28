package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Set;

import datatypes.Person;
import reader.DataReader;
import reader.ReaderException;
import searching.SearchParameters;

public class SocketSession implements AutoCloseable {

	// instance variables:
	private DataReader store;
	
	private Socket socket;
	private ObjectOutputStream oOS = null;
	private ObjectInputStream oIS = null;
	
	private boolean running = false;
	private SearchParameters searchParameters = null;
	private Set<Person> personsFound = null;
	
	// constructor:
	public SocketSession(PersonStoreSocketServer parent) throws IOException {
		if (parent == null)
			throw new NullPointerException("You cannot instantiate a SocketSession object for null as field 'parent'");
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
	public void start() throws ReaderException {
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
		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
		} finally {
			running = false;
			this.oIS = null;  // clear them from instance-scope
			this.oOS = null;
		}
	}
	
	// receiver:
	private void receive() throws ReaderException, IOException, ClassNotFoundException, ClassCastException {
		Object objectReceived = oIS.readObject();
		searchParameters = (SearchParameters)objectReceived;
		search();
		send();
	}
	
	private void search() throws ReaderException {
		personsFound = store.getPersons(searchParameters);
	}
	
	// sender:
	private void send() throws IOException {
		oOS.writeObject(personsFound);
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

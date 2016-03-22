package servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import datatypes.Person;
import searching.SearchType;

public class SocketClient implements AutoCloseable {

	// instance variables:
	private Socket socket;
	
	// constructor:
	public SocketClient(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
	}

	// querier:
	public List<Person> getPersons(String searchCriteria, SearchType searchType) throws IOException, ClassNotFoundException, ClassCastException {
		Set<Person> persons = null;
		try (	ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream())) {
			oOS.writeObject(searchType);
			oOS.writeObject(searchCriteria);
			persons = (Set<Person>)oIS.readObject();
		}
		return new ArrayList<Person>(persons);
	}
	
	// closer:
	@Override
	public void close() throws IOException {
		socket.close();
	}
	
}

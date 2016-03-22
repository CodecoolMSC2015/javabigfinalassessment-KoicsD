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

	// enumeration-type for creating order:
	public static enum OrderType { MAX, AVG }
	
	// the only instance variable:
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
		switch (searchType) {
		case MANDATORY:
			return orderPersons(persons, OrderType.AVG);
		case OPTIONAL:
			return orderPersons(persons, OrderType.MAX);
		}
		return null;  // to make compiler calm
	}
	
	public static List<Person> orderPersons(Set<Person> setOfPersons, OrderType orderBy) {
		// TODO here to convert Set to List, making an order
		return new ArrayList<Person>(setOfPersons);
	}
	
	// closer:
	@Override
	public void close() throws IOException {
		socket.close();
	}
	
}

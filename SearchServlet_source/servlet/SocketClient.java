package servlet;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
	public Set<Person> getPersons(String searchCriteria, SearchType searchType) {
		// TODO sending to and receiving from SocketServer here
		return null;
	}
	
	// closer:
	@Override
	public void close() throws IOException {
		socket.close();
	}
	
}

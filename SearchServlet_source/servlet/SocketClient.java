package servlet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import datatypes.Person;
import searching.SearchParameters;
import tools.PersonSkillStatistics;

public class SocketClient implements AutoCloseable {

	// enumeration-type for creating order:
	private static enum OrderType { MAX, AVG }
	
	// the only instance variable:
	private Socket socket;
	
	// constructor:
	public SocketClient(String host, int port) throws UnknownHostException, IOException {
		if (host == null)
			throw new NullPointerException("Parameter 'host' cannot be null when instantiating a servlet.SocketClient");
		socket = new Socket(host, port);
	}

	// querier:
	public List<Person> getPersons(SearchParameters searchParameters) throws IOException, ClassNotFoundException, ClassCastException {
		if (searchParameters == null)
			throw new NullPointerException("Method servlet.SocketClient.getPersons cannot be invoked with null as 'searchParameters'");
		Set<Person> persons;
		try (	ObjectOutputStream oOS = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream oIS = new ObjectInputStream(socket.getInputStream())) {
			oOS.writeObject(searchParameters);
			persons = (Set<Person>)oIS.readObject();
		}
		switch (searchParameters.getSearchType()) {
		case MANDATORY:
			return orderPersons(persons, searchParameters.getSearchCriteria(), OrderType.AVG);
		case OPTIONAL:
			return orderPersons(persons, searchParameters.getSearchCriteria(), OrderType.MAX);
		default:  // unreachable
			throw new IllegalArgumentException("No valid SearchType specified in 'searchParameters' in method servlet.SocketClient.getPersons");
		}
	}
	
	// private assistant function to make an ordered list:
	private static List<Person> orderPersons(Set<Person> setOfPersons, Set<String> skillNamesToConsider, OrderType orderBy) {
		List<Person> listOfPersons = new ArrayList<Person>(setOfPersons);
		switch (orderBy) {
		case MAX:
			listOfPersons.sort(new Comparator<Person>(){
				@Override
				public int compare(Person p1, Person p2) {
					double maxSkillRate1 = PersonSkillStatistics.getMaxSkillRate(p1, skillNamesToConsider);
					double maxSkillRate2 = PersonSkillStatistics.getMaxSkillRate(p2, skillNamesToConsider);
					if (maxSkillRate1 > maxSkillRate2)
						return -1;
					if (maxSkillRate1 < maxSkillRate2)
						return 1;
					return 0;
				}
			});
			break;
		case AVG:
			listOfPersons.sort(new Comparator<Person>(){
				@Override
				public int compare(Person p1, Person p2) {
					double avgSkillRate1 = PersonSkillStatistics.getAverageSkillRate(p1, skillNamesToConsider);
					double avgSkillRate2 = PersonSkillStatistics.getAverageSkillRate(p2, skillNamesToConsider);
					if (avgSkillRate1 > avgSkillRate2)
						return -1;
					if (avgSkillRate1 < avgSkillRate2)
						return 1;
					return 0;
				}
			});
			break;
		}
		return listOfPersons;
	}
	
	// closer:
	@Override
	public void close() throws IOException {
		socket.close();
	}
	
}

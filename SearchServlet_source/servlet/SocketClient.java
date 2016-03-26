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
import searching.DefaultCaseException;
import searching.SearchParameters;
import searching.SearchType;

public class SocketClient implements AutoCloseable {

	// enumeration-type for creating order:
	private static enum OrderType { MAX, AVG }
	
	// the only instance variable:
	private Socket socket;
	
	// constructor:
	public SocketClient(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket(host, port);
	}

	// querier:
	public List<Person> getPersons(SearchParameters searchParameters) throws IOException, ClassNotFoundException, ClassCastException {
		Set<Person> persons = null;
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
		default:
			throw new DefaultCaseException("No SearchType specified in searchParameters: search.SearchParameters");
		}
	}
	
	// private assistant function to make an ordered list:
	private static List<Person> orderPersons(Set<Person> setOfPersons, Set<String> skillNamesToConsider, OrderType orderBy) {
		List<Person> listOfPersons = new ArrayList<Person>(setOfPersons);
		switch (orderBy) {
		case MAX:
			listOfPersons.sort(new Comparator<Person>(){
				@Override
				public int compare(Person o1, Person o2) {
					if (o1.getMaxSkillRate(skillNamesToConsider) > o2.getMaxSkillRate(skillNamesToConsider))
						return -1;
					if (o1.getMaxSkillRate(skillNamesToConsider) < o2.getMaxSkillRate(skillNamesToConsider))
						return 1;
					return 0;
				}
			});
			break;
		case AVG:
			listOfPersons.sort(new Comparator<Person>(){
				@Override
				public int compare(Person o1, Person o2) {
					if (o1.getAverageSkillRate(skillNamesToConsider) > o2.getAverageSkillRate(skillNamesToConsider))
						return -1;
					if (o1.getAverageSkillRate(skillNamesToConsider) < o2.getAverageSkillRate(skillNamesToConsider))
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

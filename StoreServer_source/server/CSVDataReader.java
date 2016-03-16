package server;

import java.util.List;
import java.util.Set;

import datatypes.Person;
import searching.SearchType;

public class CSVDataReader extends DataReader {

	// instance variables:
	private String csvFilePath;
	private List<Person> persons;
	
	// constructors:
	public CSVDataReader(String csvFilePath) {
		super();
		this.csvFilePath = csvFilePath;
	}
	
	public CSVDataReader(String searchCriteria, SearchType searchType, String csvFilePath) {
		super(searchCriteria, searchType);
		this.csvFilePath = csvFilePath;
	}

	// searcher:
	@Override
	Set<Person> getPersons(String searchCriteria, SearchType searchType) {
		// TODO search engine comes here
		return null;
	}

}

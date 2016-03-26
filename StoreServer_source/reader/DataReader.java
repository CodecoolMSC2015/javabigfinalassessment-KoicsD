package reader;

import java.io.IOException;
import java.util.Set;

import datatypes.Person;
import searching.SearchType;

public abstract class DataReader {

	// instance variables:
	private Set<String> searchCriteria;
	private SearchType searchType;

	// constructors:
	public DataReader() {
	}

	public DataReader(Set<String> searchCriteria, SearchType searchType) {
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
	}

	// getters and setters:
	public Set<String> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(Set<String> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	
	// abstract of searcher:
	public Set<Person> getPersons() throws IOException {
		return getPersons(searchCriteria, searchType);
	}
	
	public abstract Set<Person> getPersons(Set<String> searchCriteria, SearchType searchType) throws IOException;

}

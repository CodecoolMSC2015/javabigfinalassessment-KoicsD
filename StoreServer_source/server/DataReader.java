package server;

import java.util.Set;

import datatypes.Person;
import searching.SearchType;

public abstract class DataReader {

	// instance variables:
	private String searchCriteria;
	private SearchType searchType;

	// constructors:
	public DataReader() {
	}

	public DataReader(String searchCriteria, SearchType searchType) {
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
	}

	// getters and setters:
	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	
	// abstract of searcher:
	Set<Person> getPersons() {
		return getPersons(searchCriteria, searchType);
	}
	
	abstract Set<Person> getPersons(String searchCriteria, SearchType searchType);

}
package reader;

import java.io.IOException;
import java.util.Set;

import datatypes.Person;
import searching.DefaultCaseException;
import searching.SearchParameters;
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
	
	public DataReader(SearchParameters searchParameters) {
		this.searchCriteria = searchParameters.getSearchCriteria();
		this.searchType = searchParameters.getSearchType();
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
	
	public SearchParameters getSearchParameters() {
		return new SearchParameters(searchCriteria, searchType);
	}
	
	public void setSearchParameters(SearchParameters searchParameters) {
		this.searchCriteria = searchParameters.getSearchCriteria();
		this.searchType = searchParameters.getSearchType();
	}
	
	// abstract of searcher:
	public Set<Person> getPersons() throws IOException {
		if (searchCriteria == null || searchType == null)
			throw new DefaultCaseException("Search parameters must be set before invoking DataReader.getPerson with no arguments.");
		return getPersons(searchCriteria, searchType);
	}
	
	public Set<Person> getPersons(SearchParameters searchParameters) throws IOException {
		return getPersons(searchParameters.getSearchCriteria(), searchParameters.getSearchType());
	}
	
	public abstract Set<Person> getPersons(Set<String> searchCriteria, SearchType searchType) throws IOException;

}

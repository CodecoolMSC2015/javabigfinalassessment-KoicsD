package reader;

import java.util.HashSet;
import java.util.Set;

import datatypes.Person;
import searching.SearchParameters;
import searching.SearchType;

public abstract class DataReader {

	// instance variables:
	private Set<String> searchCriteria;
	private SearchType searchType;

	// constructors:
	public DataReader() {
		this.searchCriteria = new HashSet<String>();
		this.searchType = SearchType.MANDATORY;
	}

	public DataReader(Set<String> searchCriteria, SearchType searchType) {
		if (searchCriteria == null || searchType == null)
			throw new NullPointerException("Neither field 'searchCriteria' nor field 'searchType' can be null in a DataReader object");
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
	}
	
	public DataReader(SearchParameters searchParameters) {
		if (searchParameters == null)
			throw new NullPointerException("You cannot instantiate a DataReader object for null as 'searchParameters'");
		this.searchCriteria = searchParameters.getSearchCriteria();
		this.searchType = searchParameters.getSearchType();
	}

	// getters and setters:
	public Set<String> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(Set<String> searchCriteria) {
		if (searchCriteria == null)
			throw new NullPointerException("Field 'searchCriteria' cannot be null in a DataReader object");
		this.searchCriteria = searchCriteria;
	}

	public SearchType getSearchType() {
		return searchType;
	}

	public void setSearchType(SearchType searchType) {
		if (searchType == null)
			throw new NullPointerException("Field 'searchType' cannot be null in a DataReader object");
		this.searchType = searchType;
	}
	
	public SearchParameters getSearchParameters() {
		return new SearchParameters(searchCriteria, searchType);
	}
	
	public void setSearchParameters(SearchParameters searchParameters) {
		if (searchParameters == null)
			throw new NullPointerException("Parameter 'searchParameters' cannot be null when setting fields of a DataReader object");
		this.searchCriteria = searchParameters.getSearchCriteria();
		this.searchType = searchParameters.getSearchType();
	}
	
	// abstract of searcher:
	public Set<Person> getPersons() throws ReaderException {
		return getPersons(searchCriteria, searchType);
	}
	
	public Set<Person> getPersons(SearchParameters searchParameters) throws ReaderException {
		if (searchParameters == null)
			throw new NullPointerException("You cannot invoke getPersons with null as 'searchParameters'");
		return getPersons(searchParameters.getSearchCriteria(), searchParameters.getSearchType());
	}
	
	public abstract Set<Person> getPersons(Set<String> searchCriteria, SearchType searchType) throws ReaderException;

}

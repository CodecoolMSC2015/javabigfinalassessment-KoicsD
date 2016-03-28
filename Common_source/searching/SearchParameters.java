package searching;

import java.io.Serializable;
import java.util.Set;

public class SearchParameters implements Serializable {
	
	// variables:
	private Set<String> searchCriteria;
	private SearchType searchType;
	
	// constructor:
	public SearchParameters(Set<String> searchCriteria, SearchType searchType) {
		if (searchCriteria == null || searchType == null)
			throw new NullPointerException("Neither field 'searchCriteria' nor field 'searchType' can be null in SearchParameters");
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
	}
	
	// getters:
	public Set<String> getSearchCriteria() {
		return searchCriteria;
	}
	
	public SearchType getSearchType() {
		return searchType;
	}
	
}

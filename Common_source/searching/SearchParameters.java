package searching;

import java.io.Serializable;
import java.util.Set;

public class SearchParameters implements Serializable {
	
	// variables:
	private Set<String> searchCriteria;
	private SearchType searchType;
	
	// constructor:
	public SearchParameters(Set<String> searchCriteria, SearchType searchType) {
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

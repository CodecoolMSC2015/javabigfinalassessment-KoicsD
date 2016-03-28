package searchparams;

import java.io.Serializable;
import java.util.Set;

public class SearchParameters implements Serializable {
	
	// variables:
	private Set<String> searchCriteria;
	private SearchType searchType;
	
	// constructor:
	public SearchParameters(Set<String> searchCriteria, SearchType searchType) {
		if (searchCriteria == null || searchType == null)
			throw new NullPointerException("Neither field 'searchCriteria' nor field 'searchType' can be null in searchparams.SearchParameters");
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

	// without overriding hashCode and equals, containsKey of client-side cache-HashMap always returns false
	// overriding helps even though we have to use both searchType and searchCriteria -- I don't know the reason
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((searchCriteria == null) ? 0 : searchCriteria.hashCode());
		result = prime * result + ((searchType == null) ? 0 : searchType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchParameters other = (SearchParameters) obj;
		if (searchCriteria == null) {
			if (other.searchCriteria != null)
				return false;
		} else if (!searchCriteria.equals(other.searchCriteria))
			return false;
		if (searchType != other.searchType)
			return false;
		return true;
	}
	
}

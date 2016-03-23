package searching;

public class SearchParameters {
	
	// variables:
	private String searchCriteria;
	private SearchType searchType;
	// TODO why not to send such things via socket instead of searchCriteria and searchType separately?
	
	// constructor:
	public SearchParameters(String searchCriteria, SearchType searchType) {
		this.searchCriteria = searchCriteria;
		this.searchType = searchType;
	}
	
	// getters:
	public String getSearchCriteria() {
		return searchCriteria;
	}
	
	public SearchType getSearchType() {
		return searchType;
	}
	
}

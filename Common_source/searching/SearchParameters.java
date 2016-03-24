package searching;

public class SearchParameters {
	
	// variables:
	private String searchCriteria;
	private SearchType searchType;
	// TODO why not to send such things via socket instead of searchCriteria and searchType separately?
	// TODO why not to use (Hash)Set instead of string as searchCriteria?
	
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

package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// https://sourceforge.net/projects/javacsv/
import com.csvreader.CsvReader;

import datatypes.Person;
import searching.SearchType;

public class CSVDataReader extends DataReader {
	
	// csv header:
	private static final String[] header = { "Name", "Email", "Skill", "SkillDescription", "SkillRate", "Salary" };

	// instance variables:
	private String csvFilePath;
	private List<Person> persons = new ArrayList<Person>();
	private boolean fileParsed = false;
	
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
	Set<Person> getPersons(String searchCriteria, SearchType searchType) throws IOException {
		List<Person> matches = new ArrayList<Person>();
		if (!fileParsed)
			parseFile();
		// TODO search engine comes here
		return new HashSet<Person>(matches);
	}
	
	private void parseFile() throws IOException {
		// TODO csv file handling comes here
	}

}

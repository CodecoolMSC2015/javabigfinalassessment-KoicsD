package reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


// https://sourceforge.net/projects/javacsv/
import com.csvreader.CsvReader;

import datatypes.Employee;
import datatypes.Person;
import datatypes.Skill;
import searching.SearchParameters;
import searching.SearchType;

public class CSVDataReader extends DataReader {
	
	// CSV-header:
	private static final String[] CSV_HEADER = { "Name", "Email", "Skill", "SkillDescription", "SkillRate", "Salary" };

	// instance variables:
	private File csvFile;
	private List<Person> persons;
	private long fileLastParsed = 0l;

	// constructors:
	public CSVDataReader(String csvFilePath) throws ReaderException {
		super();
		if (csvFilePath == null)
			throw new NullPointerException("You cannot instantiate a CSVDataReader for null as 'csvFilePath'");
		this.csvFile = new File(csvFilePath);
		if (!this.csvFile.exists() || this.csvFile.length() == 0)
			throw new ReaderException("The given CSV-file either does not exist or is empty");
	}

	public CSVDataReader(String csvFilePath, Set<String> searchCriteria, SearchType searchType) throws ReaderException {
		super(searchCriteria, searchType);
		if (csvFilePath == null)
			throw new NullPointerException("You cannot instantiate a CSVDataReader for null as 'csvFilePath'");
		this.csvFile = new File(csvFilePath);
		if (!this.csvFile.exists() || this.csvFile.length() == 0)
			throw new ReaderException("The given CSV-file either does not exist or is empty");
	}

	public CSVDataReader(String csvFilePath, SearchParameters searchParameters) throws ReaderException {
		super(searchParameters);
		if (csvFilePath == null)
			throw new NullPointerException("You cannot instantiate a CSVDataReader for null as 'csvFilePath'");
		this.csvFile = new File(csvFilePath);
		if (!this.csvFile.exists() || this.csvFile.length() == 0)
			throw new ReaderException("The given CSV-file either does not exist or is empty");
	}

	// searcher:
	@Override
	public Set<Person> getPersons(Set<String> searchCriteria, SearchType searchType) throws ReaderException {
		if (searchCriteria == null || searchType == null)
			throw new NullPointerException("Neither parameter 'searchCriteria' nor parameter 'searchType' can be null when invoking DataReader.getPersons");
		Set<Person> matches = new HashSet<Person>();
		if (fileLastParsed < csvFile.lastModified())
			parseFile();
		for (Person person: persons) {
			switch (searchType) {
			case MANDATORY:
				if (hasPersonGotAllSkills(person, searchCriteria))
					matches.add(person);
				break;
			case OPTIONAL:
				if (hasPersonGotAnySkills(person, searchCriteria))
					matches.add(person);
				break;
			default:  // unreachable, but somehow we have to make compiler calm
				throw new IllegalArgumentException("No valid SearchType specified in CSVDataReader.getPersons");
			}
		}
		return matches;
	}
	
	// private assistants of searcher:
	private boolean hasPersonGotAnySkills(Person person, Set<String> criteriumSkillNames) {
		Set<String> personSkillNames = person.getSkillNameSet();
		for (String skillName: criteriumSkillNames) {
			if (personSkillNames.contains(skillName))
				return true;
		}
		return false;
	}
	
	private boolean hasPersonGotAllSkills(Person person, Set<String> criteriumSkillNames) {
		Set<String> personSkillNames = person.getSkillNameSet();
		for (String skillName: criteriumSkillNames) {
			if (!personSkillNames.contains(skillName))
				return false;
		}
		return true;
	}
	
	// CSV data-parser:
	private void parseFile() throws ReaderException {
		CsvReader reader = null;
		try {  // to pack IOException into ReaderException
			reader = new CsvReader(csvFile.getAbsolutePath());
			
			if (!reader.readHeaders() || !Arrays.equals(CSV_HEADER, reader.getHeaders()))
				throw new IOException("Invalid CSV-file header");
			List<Person> persons = new ArrayList<Person>();
			{  // intermediate block for loop-variables
				String name;
				String email;
				String skill;
				String skillDescription;
				String skillRate = null;
				String salary = null;
				Person currentPerson;
				Skill currentSkill;
				int i;
				for (int line=0; reader.readRecord(); ++line) {
					try {  // to catch NumberFormatException
						name = reader.get("Name");
						email = reader.get("Email");
						salary = reader.get("Salary");
						if (salary.length() > 0) {
							currentPerson = new Employee(name, email, Integer.valueOf(salary));
						} else {
							currentPerson = new Person(name, email);
						}
						skill = reader.get("Skill");
						skillDescription = reader.get("SkillDescription");
						skillRate = reader.get("SkillRate");
						currentSkill = new Skill(skill,skillDescription, Double.valueOf(skillRate));
						if (persons.contains(currentPerson)) {
							i = persons.indexOf(currentPerson);
							currentPerson = persons.get(i);
							currentPerson.addSkill(currentSkill);
						} else {
							currentPerson.addSkill(currentSkill);
							persons.add(currentPerson);
						}
					} catch (NumberFormatException e) {
						System.err.println("A NumberFormatException occurred when parsing CSV-file.");
						System.err.println("File: " + csvFile.getAbsolutePath());
						System.err.println("Line number: " + line);
						System.err.println("Line content: " + reader.getRawRecord());
						if (salary != null)
							System.err.println("Salary as String: " + salary);
						if (skillRate != null)
							System.err.println("SkillRate as String: " + skillRate);
						if (e.getMessage() != null)
							System.err.println("Message: " + e.getMessage());
					} finally {
						salary = null;
						skillRate = null;
					}
				}  // for-loop
			}  // intermediate block for loop-variables
			this.persons = persons;
		} catch (IOException iOException) {
			throw new ReaderException("An IOException occurred while parsing CSV-file", iOException);
		} finally {
			if (reader != null)
				reader.close();
		}
		fileLastParsed = csvFile.lastModified();
	}

}

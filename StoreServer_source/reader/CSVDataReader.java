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
import searching.DefaultCaseException;
import searching.SearchType;

public class CSVDataReader extends DataReader {
	
	// csv header:
	private static final String[] CSV_HEADER = { "Name", "Email", "Skill", "SkillDescription", "SkillRate", "Salary" };

	// instance variables:
	private File csvFile;
	private List<Person> persons;
	private long fileLastParsed = 0l;
	
	// constructors:
	public CSVDataReader(String csvFilePath) {
		super();
		this.csvFile = new File(csvFilePath);
	}
	
	public CSVDataReader(String searchCriteria, SearchType searchType, String csvFilePath) {
		super(searchCriteria, searchType);
		this.csvFile = new File(csvFilePath);
	}

	// searcher:
	@Override
	public Set<Person> getPersons(String searchCriteria, SearchType searchType) throws IOException {
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
			default:
				throw new DefaultCaseException("No SearchType specified in CSVDataReader.getPersons");
			}
		}
		return matches;
	}
	
	// private assistants of searcher:
	private boolean hasPersonGotAnySkills(Person person, String criteriumSkillNames) {
		Set<String> personSkillNames = getSkillNameSet(person);
		for (String skillName: criteriumSkillNames.split(";")) {
			if (personSkillNames.contains(skillName))
				return true;
		}
		return false;
	}
	
	private boolean hasPersonGotAllSkills(Person person, String criteriumSkillNames) {
		Set<String> personSkillNames = getSkillNameSet(person);
		for (String skillName: criteriumSkillNames.split(";")) {
			if (!personSkillNames.contains(skillName))
				return false;
		}
		return true;
	}
	
	private Set<String> getSkillNameSet(Person person) {
		Set<String> skillNames = new HashSet<String>();
		for (Skill skill: person.getSkillset()) {
			skillNames.add(skill.getName());
		}
		return skillNames;
	}
	
	// CSV data-parser:
	private void parseFile() throws IOException {
		CsvReader reader = null;
		try  {
			reader = new CsvReader(csvFile.getAbsolutePath());
			
			if (!reader.readHeaders() || !Arrays.equals(CSV_HEADER, reader.getHeaders()))
				throw new IOException("Invalid CSV-file header");
			List<Person> persons = new ArrayList<Person>();
			{
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
					try {
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
				}
			}
			this.persons = persons;
		} finally {
			if (reader != null)
				reader.close();
		}
		fileLastParsed = csvFile.lastModified();
	}

}

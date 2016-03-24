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
import searching.SearchType;

public class CSVDataReader extends DataReader {
	
	// csv header:
	private static final String[] CSV_HEADER = { "Name", "Email", "Skill", "SkillDescription", "SkillRate", "Salary" };

	// instance variables:
	private String csvFilePath;
	private List<Person> persons;
	private boolean fileParsed = false;  // TODO how about using a date-stamp instead of boolean? Parse only if not up-to-date.
	
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
	public Set<Person> getPersons(String searchCriteria, SearchType searchType) throws IOException {
		List<Person> matches = new ArrayList<Person>();
		if (!fileParsed)
			parseFile();
		Set<Skill> criteriaSkillSet = convertCriteriaToSet(searchCriteria);
		for (Person person: persons) {
			switch (searchType) {
			case MANDATORY:
				if (new HashSet<>(person.getSkillset()).containsAll(criteriaSkillSet))
					matches.add(person);
				break;
			case OPTIONAL:
				if (isThereAnyCommonElement(criteriaSkillSet, new HashSet<Skill>(person.getSkillset())))
					matches.add(person);
				break;
			}
		}
		return new HashSet<Person>(matches);
	}
	
	private boolean isThereAnyCommonElement(Set<Skill>a, Set<Skill> b) {
		Set<Skill> c = new HashSet<Skill>(a);
		c.retainAll(b);
		return (c.size() > 0);
	}
	
	private Set<Skill> convertCriteriaToSet(String searchCriteria) {
		Set<Skill> criteriaSet = new HashSet<Skill>();
		for (String criterium: searchCriteria.split(";")) {
			criteriaSet.add(new Skill(criterium, "", 0));
		}
		return criteriaSet;
	}
	
	private void parseFile() throws IOException {
		CsvReader reader = null;
		try  {
			reader = new CsvReader(csvFilePath);
			
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
						System.err.println("File: " + new File(csvFilePath).getAbsolutePath());
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
			fileParsed = true;
		} finally {
			if (reader != null)
				reader.close();
		}
	}

}

package datatypes;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {

	// instance variables:
	private String name;
	private String email;
	private List<Skill> skillset;
	
	// constructors:
	public Person(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	public Person() {
		
	}

	// getters and setters (and skill-adder):
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Skill> getSkillset() {
		return skillset;
	}

	public void setSkillset(List<Skill> skillset) {
		this.skillset = skillset;
	}
	
	public void addSkill(Skill skill) {
		// TODO: what if (skillset == null) ?
		skillset.add(skill);
	}
	
}
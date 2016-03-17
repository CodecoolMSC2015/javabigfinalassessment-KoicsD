package datatypes;

import java.io.Serializable;
import java.util.ArrayList;
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
		skillset = new ArrayList<Skill>();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Person other = (Person) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}

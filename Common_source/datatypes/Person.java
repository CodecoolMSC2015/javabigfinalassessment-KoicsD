package datatypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import tools.HtmlCompatible;

public class Person implements Serializable, HtmlCompatible {

	// instance variables:
	private String name;
	private String email;
	private Set<Skill> skillSet;
	
	// constructors:
	public Person(String name, String email) {
		if (name == null || email == null)
			throw new NullPointerException("Neither field 'name', nor field 'email' can be null in a Person object");
		this.name = name;
		this.email = email;
		this.skillSet = new HashSet<Skill>();
	}

	public Person(String name, String email, Set<Skill> skillSet) {
		if (name == null || email == null || skillSet == null)
			throw new NullPointerException("Neither field 'name', nor field 'email', nor field 'skillSet' can be null in a Person object");
		this.name = name;
		this.email = email;
		this.skillSet = skillSet;
	}

	// getters and setters:
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null)
			throw new NullPointerException("Field 'name' cannot be null in a Person object");
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null)
			throw new NullPointerException("Field 'email' cannot be null in a Person object");
		this.email = email;
	}

	public Set<Skill> getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(Set<Skill> skillSet) {
		if (skillSet == null)
			throw new NullPointerException("Field 'skillSet' cannot be null in a Person object");
		this.skillSet = skillSet;
	}
	
	// further tools for skillSet:
	public void addSkill(Skill skill) {
		if (skill == null)
			throw new NullPointerException("null cannot be added to a Person object as skill");
		skillSet.add(skill);
	}
	
	public void removeSkill(Skill skill) {
		skillSet.remove(skill);
	}
	
	public Set<String> getSkillNameSet() {
		Set<String> skillNames = new HashSet<String>();
		for (Skill skill: skillSet) {
			skillNames.add(skill.getName());
		}
		return skillNames;
	}
	
	public int getNumberOfSkills() {
		return skillSet.size();
	}
	
	// overriding idea of equivalent:
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

	// implementing to-HTML converter:
	@Override
	public String toHtmlString() {
		String asHtml = getName() + "<br/>";
		asHtml += "&nbsp;&nbsp;Email: " + getEmail() + "<br/>";
		if (!getSkillSet().isEmpty()) {
			asHtml += "&nbsp;&nbsp;Skills:<br/>";
			for (Skill skill: getSkillSet()) {
				asHtml += "&nbsp;&nbsp;&nbsp;&nbsp;" + skill.getName() + "&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
			}
		}
		return asHtml;
	}
	
}

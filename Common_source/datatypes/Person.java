package datatypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import tools.HtmlCompatible;

public class Person implements Serializable, HtmlCompatible {
	// TODO it would be better to delete empty constructor

	// instance variables:
	private String name;
	private String email;
	private Set<Skill> skillSet;
	
	// constructors:
	public Person(String name, String email) {
		super();
		this.name = name;
		this.email = email;
		skillSet = new HashSet<Skill>();
	}
	
	public Person() {
		
	}

	// getters and setters:
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

	public Set<Skill> getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(Set<Skill> skillSet) {
		this.skillSet = skillSet;
	}
	
	// further tools for skillSet:
	public void addSkill(Skill skill) {
		// TODO: what if (skillset == null) ?
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

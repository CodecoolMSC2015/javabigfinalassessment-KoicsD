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

	public Set<Skill> getSkillSet() {
		return skillSet;
	}

	public void setSkillSet(Set<Skill> skillSet) {
		this.skillSet = skillSet;
	}
	
	// skill-adder:
	public void addSkill(Skill skill) {
		// TODO: what if (skillset == null) ?
		skillSet.add(skill);
	}
	
	// getter for skill-names as Set:
	public Set<String> getSkillNameSet() {
		Set<String> skillNames = new HashSet<String>();
		for (Skill skill: skillSet) {
			skillNames.add(skill.getName());
		}
		return skillNames;
	}
	
	// statistical functions:  TODO move them to a separate tool-class in order to avoid god-object effect
	public double getMaxSkillRate() {
		return getMaxSkillRate(getSkillNameSet());
	}
	
	public double getMaxSkillRate(Set<String> skillNamesToConsider) {
		double maxSkillRate = 0;
		boolean first = true;
		for (Skill skill: skillSet) {
			if (skillNamesToConsider.contains(skill.getName())) {
				if (first) {
					maxSkillRate = skill.getRate();
					first = false;
				} else if (skill.getRate() > maxSkillRate) {
					maxSkillRate = skill.getRate();
				}
			}
		}
		return maxSkillRate;
	}
	
	public double getAverageSkillRate() {
		return getTotalSkillRate() / getNumberOfSkills();
	}
	
	public double getAverageSkillRate(Set<String> skillNamesToConsider) {
		return getTotalSkillRate(skillNamesToConsider) / getNumberOfSkills(skillNamesToConsider);
	}
	
	public int getNumberOfSkills() {
		return skillSet.size();
	}
	
	public int getNumberOfSkills(Set<String> skillNamesToConsider) {
		int numberOfSkills = 0;
		for (Skill skill: skillSet)
			if (skillNamesToConsider.contains(skill.getName()))
				++ numberOfSkills;
		return numberOfSkills;
	}
	
	public double getTotalSkillRate() {
		return getTotalSkillRate(getSkillNameSet());
	}
	
	public double getTotalSkillRate(Set<String> skillNamesToConsider) {
		double totalSkillRate = 0;
		for (Skill skill: skillSet) {
			if (skillNamesToConsider.contains(skill.getName()))
				totalSkillRate += skill.getRate();
		}
		return totalSkillRate;
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

	// implementing to-HTML converter and highlight:
	@Override
	public String toHtmlString() {
		return toHtmlString(new HashSet<String>());
	}
	
	@Override
	public String toHtmlString(Set<String> skillNamesToHighlight) {
		String asHtml = getName() + "<br/>";
		asHtml += "&nbsp;&nbsp;Email: " + getEmail() + "<br/>";
		if (!getSkillSet().isEmpty()) {
			asHtml += "&nbsp;&nbsp;Skills:<br/>";
			for (Skill skill: getSkillSet()) {
				if (skillNamesToHighlight.contains(skill.getName()))
					asHtml += "&nbsp;&nbsp;&nbsp;&nbsp;<mark>" + skill.getName() + "</mark>&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
				else
					asHtml += "&nbsp;&nbsp;&nbsp;&nbsp;" + skill.getName() + "&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
			}
		}
		return asHtml;
	}
	
}

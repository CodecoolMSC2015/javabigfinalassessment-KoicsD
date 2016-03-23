package datatypes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tools.HtmlCompatible;

public class Person implements Serializable, HtmlCompatible {

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
	
	public double getMaxSkillRate() {
		double maxSkillRate = 0;
		boolean first = true;
		for (Skill skill: skillset) {
			if (first) {
				maxSkillRate = skill.getRate();
				first = false;
			} else if (skill.getRate() > maxSkillRate) {
				maxSkillRate = skill.getRate();
			}
		}
		return maxSkillRate;
	}
	
	public double getAverageSkillRate() {
		return getTotalSkillRate() / getNumberOfSkills();
	}
	
	public int getNumberOfSkills() {
		return skillset.size();
	}
	
	public double getTotalSkillRate() {
		double totalSkillRate = 0;
		for (Skill skill: skillset) {
			totalSkillRate += skill.getRate();
		}
		return totalSkillRate;
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

	@Override
	public String toHtmlString() {
		String asHtml = getName() + "<br/>";
		asHtml += "&nbsp;&nbsp;Email: " + getEmail() + "<br/>";
		if (!getSkillset().isEmpty()) {
			asHtml += "&nbsp;&nbsp;Skills:<br/>";
			for (Skill skill: getSkillset()) {
				asHtml += "&nbsp;&nbsp;&nbsp;&nbsp;" + skill.getName() + "&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
			}
		}
		return asHtml;
	}
	
}

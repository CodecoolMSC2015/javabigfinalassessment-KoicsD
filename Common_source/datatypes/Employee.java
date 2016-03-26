package datatypes;

import java.util.HashSet;
import java.util.Set;

public class Employee extends Person {
	// TODO it would be better to delete empty constructor
	
	// instance variables:
	private int salary;
	private String jobTitle;
	
	public Employee() {
		super();
	}
	
	public Employee(String name, String email, int salary) {
		super(name, email);
		this.salary = salary;
	}
	
	// getters and setters:
	public int getSalary() {
		return salary;
	}
	
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public String getJobTitle() {
		return jobTitle;
	}
	
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	@Override
	public String toHtmlString() {
		return toHtmlString(new HashSet<String>());
	}
	
	@Override
	public String toHtmlString(Set<String> skillNamesToHighlight) {
		String asHtml = getName() + "<br/>";
		asHtml += "&nbsp;&nbsp;Email: " + getEmail() + "<br/>";
		asHtml += "&nbsp;&nbsp;Salary: " + getSalary() + "<br/>";
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

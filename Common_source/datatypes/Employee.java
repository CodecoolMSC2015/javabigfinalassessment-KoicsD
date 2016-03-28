package datatypes;

import java.util.Set;

public class Employee extends Person {
	// TODO if we want to store jobTitle, why is it not present in CSV-file?
	
	// instance variables:
	private int salary;
	private String jobTitle;
	
	public Employee(String name, String email, int salary) {
		super(name, email);
		this.jobTitle = "";
		this.salary = salary;
	}
	
	public Employee(String name, String email, Set<Skill> skillSet, int salary) {
		super(name, email, skillSet);
		this.jobTitle = "";
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
		if (jobTitle == null)
			throw new NullPointerException("Field 'jobTitle' cannot be null in an Employee object");
		this.jobTitle = jobTitle;
	}
	
	// overriding to-HTML converter:
	@Override
	public String toHtmlString() {
		String asHtml = getName() + "<br/>";
		asHtml += "&nbsp;&nbsp;Email: " + getEmail() + "<br/>";
		asHtml += "&nbsp;&nbsp;Salary: " + getSalary() + "<br/>";
		if (!getSkillSet().isEmpty()) {
			asHtml += "&nbsp;&nbsp;Skills:<br/>";
			for (Skill skill: getSkillSet()) {
				asHtml += "&nbsp;&nbsp;&nbsp;&nbsp;" + skill.getName() + "&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
			}
		}
		return asHtml;
	}
	
}

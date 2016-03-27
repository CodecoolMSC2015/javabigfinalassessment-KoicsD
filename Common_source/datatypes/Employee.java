package datatypes;

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

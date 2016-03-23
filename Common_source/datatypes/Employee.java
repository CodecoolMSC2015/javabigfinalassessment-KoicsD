package datatypes;

public class Employee extends Person {
	
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
		String asHtml = getName();
		asHtml += "&nbsp;&nbsp;Email: " + getEmail() + "<br/>";
		asHtml += "&nbsp;&nbsp;Email: " + getSalary() + "<br/>";
		if (!getSkillset().isEmpty()) {
			asHtml += "&nbsp;&nbsp;Skills:<br/>";
			for (Skill skill: getSkillset()) {
				asHtml += "&nbsp;&nbsp;&nbsp;&nbsp;" + skill.getName() + "&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
			}
		}
		return asHtml;
	}
	
}

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

	// to-string converter:
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}

package datatypes;

import java.io.Serializable;

public class Skill implements Serializable {
	
	// instance variables:
	private String name;
	private String description;
	private double rate;
	
	// constructor:
//	public Skill(String name, String description) {
	public Skill(String name, String description, double rate) {  // where to set rate if not here?
		this.name = name;
		this.description = description;
		this.rate = rate;
	}

	// getters:
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getRate() {
		return rate;
	}
	
}
package datatypes;

import java.io.Serializable;

import tools.htmlCompatible;

public class Skill implements Serializable, htmlCompatible {
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Skill other = (Skill) obj;
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
		asHtml += "&nbsp;&nbsp;Rate: " + getRate() + "<br/>";
		asHtml += "&nbsp;&nbsp;Description: " + getDescription() + "<br/>";
		return asHtml;
	}
	
}

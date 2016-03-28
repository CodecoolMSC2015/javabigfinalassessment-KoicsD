package datatypes;

import java.io.Serializable;

import tools.HtmlCompatible;

public class Skill implements Serializable, HtmlCompatible {
	
	// instance variables:
	private String name;
	private String description;
	private double rate;
	
	// constructor:
//	public Skill(String name, String description) {
	public Skill(String name, String description, double rate) {  // where to set rate if not here?
		if (name == null || description == null)
			throw new NullPointerException("Neither field 'name' nor field 'description' can be null in a datatypes.Skill object");
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

	// implementing to-HTML converter:
	@Override
	public String toHtmlString() {
		String asHtml = "";
		asHtml += getName() + "<br/>";
		asHtml += "&nbsp;&nbsp;Rate: " + getRate() + "<br/>";
		asHtml += "&nbsp;&nbsp;Description: " + getDescription() + "<br/>";
		return asHtml;
	}
	
}

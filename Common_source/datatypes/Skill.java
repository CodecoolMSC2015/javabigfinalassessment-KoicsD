package datatypes;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import tools.HtmlCompatible;

public class Skill implements Serializable, HtmlCompatible {
	
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
	public String toHtmlString() {
		return toHtmlString(new HashSet<String>());
	}
	
	@Override
	public String toHtmlString(Set<String> skillNamesToHighlight) {
		String asHtml = "";
		if (skillNamesToHighlight.contains(getName()))
			asHtml += "<mark>" + getName() + "</mark><br/>";
		else
			asHtml += getName() + "<br/>";
		asHtml += "&nbsp;&nbsp;Rate: " + getRate() + "<br/>";
		asHtml += "&nbsp;&nbsp;Description: " + getDescription() + "<br/>";
		return asHtml;
	}
	
}

package tools;

import java.util.HashSet;
import java.util.Set;

import datatypes.Employee;
import datatypes.Person;
import datatypes.Skill;

public class ToHtmlConverter {
	
	public static String convertToHtmlString(Person person) {
		return convertToHtmlString(person, new HashSet<String>());
	}
	
	public static String convertToHtmlString(Person person, Set<String> skillNamesToHighlight) {
		String personInHtml = person.getName() + "<br/>";
		personInHtml += "&nbsp;&nbsp;Email: " + person.getEmail() + "<br/>";
		if (person instanceof Employee)
			personInHtml += "&nbsp;&nbsp;Salary: " + ((Employee)person).getSalary() + "<br/>";
		if (!person.getSkillSet().isEmpty()) {
			personInHtml += "&nbsp;&nbsp;Skills:<br/>";
			for (Skill skill: person.getSkillSet()) {
				if (skillNamesToHighlight.contains(skill.getName()))
					personInHtml += "&nbsp;&nbsp;&nbsp;&nbsp;<mark>" + skill.getName() + "</mark>&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
				else
					personInHtml += "&nbsp;&nbsp;&nbsp;&nbsp;" + skill.getName() + "&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
			}
		}
		return personInHtml;
	}
	
}

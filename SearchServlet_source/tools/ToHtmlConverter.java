package tools;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import datatypes.Employee;
import datatypes.Person;
import datatypes.Skill;

public class ToHtmlConverter {
	
	// table-header for Person:
	private static final String[] PERSON_TABLE_HEADER = {"Name", "Email", "Salary", "SkillName", "SkillRate"};
	
	// tagger:
	public static String toHtmlTag(String tagName, String value) {
		return toHtmlTag(tagName, null, value);
	}
	
	public static String toHtmlTag(String tagName, Map<String, String> properties, String value) {
		String tag = "";
		tag += "<" + tagName;
		if (properties != null)
			for (String propertyName: properties.keySet()) {
				tag += " " + propertyName + "=";
				tag += "\"" + properties.get(propertyName) + "\"";
			}
		tag += ">";
		tag += value;
		tag += "</" + tagName + ">";
		return tag;
	}
	
	// familiar and highlight-capable to-HTML converter:
	public static String convertToHtmlString(Person person) {
		return convertToHtmlString(person, null);
	}
	
	public static String convertToHtmlString(Person person, Set<String> skillNamesToHighlight) {
		String personInHtml = person.getName() + "<br/>";
		personInHtml += "&nbsp;&nbsp;Email: " + person.getEmail() + "<br/>";
		if (person instanceof Employee)
			personInHtml += "&nbsp;&nbsp;Salary: " + ((Employee)person).getSalary() + "<br/>";
		if (!person.getSkillSet().isEmpty()) {
			personInHtml += "&nbsp;&nbsp;Skills:<br/>";
			for (Skill skill: person.getSkillSet()) {
				if (skillNamesToHighlight != null && MatchDefiner.doesElementMatchAnyKeys(skill.getName(), skillNamesToHighlight))
					personInHtml += "&nbsp;&nbsp;&nbsp;&nbsp;<mark>" + skill.getName() + "</mark>&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
				else
					personInHtml += "&nbsp;&nbsp;&nbsp;&nbsp;" + skill.getName() + "&nbsp;&nbsp;(" + skill.getRate() + ")<br/>";
			}
		}
		return personInHtml;
	}
	
	// getter for table-header:
	public static String getPersonTableHeader() {
		String content = "";
		for (String columnName: PERSON_TABLE_HEADER)
			content += tagAsHeaderCell(columnName);
		return tagAsRecord(content);
	}
	
	// to-table-record converters:
	public static String convertToTableRecords(Person person) {
		return convertToTableRecords(person, null);
	}
	
	public static String convertToTableRecords(Person person, Set<String> skillNamesToHighlight) {
		String[] nonSkillContent = getNonSkillContent(person);
		Set<Skill> skillSet = person.getSkillSet();
		return buildRecords(nonSkillContent, skillSet, skillNamesToHighlight);
	}
	
	// private assistants for to-table-record converters:
	private static String[] getNonSkillContent(Person person) {
		String[] nonSkillContent = new String[3];
		nonSkillContent[0] = person.getName();
		nonSkillContent[1] = person.getEmail();
		if (person instanceof Employee)
			nonSkillContent[2] = String.valueOf(((Employee)person).getSalary());
		else
			nonSkillContent[2] = "&nbsp;";
		return nonSkillContent;
	}
	
	private static String buildRecords(String[] nonSkillContent, Set<Skill> skillSet, Set<String> skillNamesToHighlight) {
		String records = ""; 
		if (skillSet.size() == 0) {
			records += tagAsRecord(
					tagAsDataCell(nonSkillContent[0], false) +
					tagAsDataCell(nonSkillContent[1], false) +
					tagAsDataCell(nonSkillContent[2], false) +
					tagAsDataCell("&nbsp;", false) +
					tagAsDataCell("&nbsp;", false)
			);
		}
		else {
			boolean firstRecord = true;
			boolean highlightNecessary;
			for (Skill skill: skillSet) {
				highlightNecessary = skillNamesToHighlight != null && MatchDefiner.doesElementMatchAnyKeys(skill.getName(), skillNamesToHighlight);
				if (firstRecord) {
					records += tagAsRecord(
							tagAsDataCell(skillSet.size(), 1, nonSkillContent[0]) +
							tagAsDataCell(skillSet.size(), 1, nonSkillContent[1]) +
							tagAsDataCell(skillSet.size(), 1, nonSkillContent[2]) +
							tagAsDataCell(skill.getName(), highlightNecessary) +
							tagAsDataCell(String.valueOf(skill.getRate()), highlightNecessary)
					);
					firstRecord = false;
				} else {
					records += tagAsRecord(
							tagAsDataCell(skill.getName(), highlightNecessary) +
							tagAsDataCell(String.valueOf(skill.getRate()), highlightNecessary)
					);
				}
			}
		}
		return records;
	}
	
	private static String tagAsDataCell(String content, boolean highlight) {
		if (highlight)
			content = toHtmlTag("mark", content);
		return toHtmlTag("td", content);
	}
	
	private static String tagAsDataCell(int rowspan, int colspan, String content) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("rowspan", String.valueOf(rowspan));
		properties.put("colspan", String.valueOf(colspan));
		return toHtmlTag("td", properties, content);
	}
	
	private static String tagAsHeaderCell(String content) {
		return toHtmlTag("th", content);
	}
	
	private static String tagAsHeaderCell(int rowspan, int colspan, String content) {
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("rowspan", String.valueOf(rowspan));
		properties.put("colspan", String.valueOf(colspan));
		return toHtmlTag("th", properties, content);
	}
	
	private static String tagAsRecord(String content) {
		return toHtmlTag("tr", content);
	}
	
}

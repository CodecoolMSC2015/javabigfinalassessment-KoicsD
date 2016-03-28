package tools;

import java.util.HashSet;
import java.util.Set;

import datatypes.Person;
import datatypes.Skill;

public class MatchDefiner {
	// Definition of MATCH here
	private static final boolean doesKeyFitElement(String key, String element) {
		return element.toLowerCase().equals(key.toLowerCase());  // DEFINITION OF MATCH
	}
	
	// methods to work with sets and strings:
	public static boolean doesElementMatchAnyKeys(String element, Set<String> keySet) {
		if (element == null || keySet == null)
			throw new NullPointerException("Neither parameter 'element' nor parameter 'keySet' can be null in method tools.MatchDefiner.doesElementMatchAnyKeys");
		for (String key: keySet) {
			if (doesKeyFitElement(key, element))
				return true;
		}
		return false;
	}
	
	public static boolean doesSetContainKey(Set<String> set, String key) {
		if (set == null || key == null)
			throw new NullPointerException("Neither parameter 'set' nor parameter 'key' can be null in method tools.MatchDefiner.doesSetContainKey");
		for (String element: set) {
			if (doesKeyFitElement(key, element))
				return true;
		}
		return false;
	}
	
	// methods to work on Persons:
	public static Set<String> getSkillNameSet(Person person) {
		if (person == null)
			throw new NullPointerException("Neither parameter 'person' cannot be null in method tools.MatchDefiner.getSkillNameSet");
		Set<String> skillNames = new HashSet<String>();
		for (Skill skill: person.getSkillSet()) {
			skillNames.add(skill.getName());
		}
		return skillNames;
	}
	
	public static boolean hasPersonGotAnySkills(Person person, Set<String> criteriumSkillNames) {
		if (person == null || criteriumSkillNames == null)
			throw new NullPointerException("Neither parameter 'person' nor parameter 'criteriumSkillNames' can be null in method tools.MatchDefiner.hasPersonGotAnySkills");
		Set<String> personSkillNames = getSkillNameSet(person);
		for (String skillName: criteriumSkillNames) {
			if (doesSetContainKey(personSkillNames, skillName))
				return true;
		}
		return false;
	}
	
	public static boolean hasPersonGotAllSkills(Person person, Set<String> criteriumSkillNames) {
		if (person == null || criteriumSkillNames == null)
			throw new NullPointerException("Neither parameter 'person' nor parameter 'criteriumSkillNames' can be null in method tools.MatchDefiner.hasPersonGotAllSkills");
		Set<String> personSkillNames = getSkillNameSet(person);
		for (String skillName: criteriumSkillNames) {
			if (!doesSetContainKey(personSkillNames, skillName))
				return false;
		}
		return true;
	}
	
}

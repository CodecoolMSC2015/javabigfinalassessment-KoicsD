package tools;

import java.util.HashSet;
import java.util.Set;

import datatypes.Person;
import datatypes.Skill;

public class MatchDefiner {
	// Definition of MATCH here
	
	public static Set<String> getSkillNameSet(Person person) {
		Set<String> skillNames = new HashSet<String>();
		for (Skill skill: person.getSkillSet()) {
			skillNames.add(skill.getName());
		}
		return skillNames;
	}
	
	public static boolean hasPersonGotAnySkills(Person person, Set<String> criteriumSkillNames) {
		Set<String> personSkillNames = getSkillNameSet(person);
		for (String skillName: criteriumSkillNames) {
			if (personSkillNames.contains(skillName))
				return true;
		}
		return false;
	}
	
	public static boolean hasPersonGotAllSkills(Person person, Set<String> criteriumSkillNames) {
		Set<String> personSkillNames = getSkillNameSet(person);
		for (String skillName: criteriumSkillNames) {
			if (!personSkillNames.contains(skillName))
				return false;
		}
		return true;
	}
	
	public static boolean doesSetContainKey(Set<String> set, String key) {
		// TODO
		return false;
	}
	
	public static boolean doesElementMatchAnyKeys(String element, Set<String> keySet) {
		// TODO
		return false;
	}
	
	private static boolean doesKeyMatchWithElement(String key, String element) {  // DEFINITION OF MATCH
		// TODO
		return false;
	}
	
}

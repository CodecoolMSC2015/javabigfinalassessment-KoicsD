package tools;

import java.util.Set;

import datatypes.Person;
import datatypes.Skill;

public class PersonSkillStatistics {
	
	public static double getMaxSkillRate(Person person) {
		return getMaxSkillRate(person, null);
	}
	
	public static double getMaxSkillRate(Person person, Set<String> skillNamesToConsider) {
		if (person == null)
			throw new NullPointerException("Parameter 'person' cannot be null in method tools.PersonStatistics.getMaxSkillRate");
		if (skillNamesToConsider == null)
			skillNamesToConsider = MatchDefiner.getSkillNameSet(person);
		double maxSkillRate = 0;
		boolean first = true;
		for (Skill skill: person.getSkillSet()) {
			if (MatchDefiner.doesElementMatchAnyKeys(skill.getName(), skillNamesToConsider)) {
				if (first) {
					maxSkillRate = skill.getRate();
					first = false;
				} else if (skill.getRate() > maxSkillRate) {
					maxSkillRate = skill.getRate();
				}
			}
		}
		return maxSkillRate;
	}
	
	public static double getAverageSkillRate(Person person) {
		return getAverageSkillRate(person, null);
	}
	
	public static double getAverageSkillRate(Person person, Set<String> skillNamesToConsider) {
		if (person == null)
			throw new NullPointerException("Parameter 'person' cannot be null in method tools.PersonStatistics.getAverageSkillRate");
		if (skillNamesToConsider == null)
			skillNamesToConsider = MatchDefiner.getSkillNameSet(person);
		return getTotalSkillRate(person, skillNamesToConsider) / getNumberOfSkills(person, skillNamesToConsider);
	}
	
	public static int getNumberOfSkills(Person person) {
		return getNumberOfSkills(person, null);
	}
	
	public static int getNumberOfSkills(Person person, Set<String> skillNamesToConsider) {
		if (person == null)
			throw new NullPointerException("Parameter 'person' cannot be null in method tools.PersonStatistics.getNumberOfSkills");
		if (skillNamesToConsider == null)
			return person.getNumberOfSkills();
		int numberOfSkills = 0;
		for (Skill skill: person.getSkillSet())
			if (MatchDefiner.doesElementMatchAnyKeys(skill.getName(), skillNamesToConsider))
				++ numberOfSkills;
		return numberOfSkills;
	}
	
	public static double getTotalSkillRate(Person person) {
		return getTotalSkillRate(person, null);
	}
	
	public static double getTotalSkillRate(Person person, Set<String> skillNamesToConsider) {
		if (person == null)
			throw new NullPointerException("Parameter 'person' cannot be null in method tools.PersonStatistics.getTotalSkillRate");
		if (skillNamesToConsider == null)
			skillNamesToConsider = MatchDefiner.getSkillNameSet(person);
		double totalSkillRate = 0;
		for (Skill skill: person.getSkillSet()) {
			if (MatchDefiner.doesElementMatchAnyKeys(skill.getName(), skillNamesToConsider))
				totalSkillRate += skill.getRate();
		}
		return totalSkillRate;
	}
	
}

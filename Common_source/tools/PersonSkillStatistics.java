package tools;

import java.util.Set;

import datatypes.Person;
import datatypes.Skill;

public class PersonSkillStatistics {
	
	public static double getMaxSkillRate(Person person) {
		return getMaxSkillRate(person, person.getSkillNameSet());
	}
	
	public static double getMaxSkillRate(Person person, Set<String> skillNamesToConsider) {
		double maxSkillRate = 0;
		boolean first = true;
		for (Skill skill: person.getSkillSet()) {
			if (skillNamesToConsider.contains(skill.getName())) {
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
		return getTotalSkillRate(person) / getNumberOfSkills(person);
	}
	
	public static double getAverageSkillRate(Person person, Set<String> skillNamesToConsider) {
		return getTotalSkillRate(person, skillNamesToConsider) / getNumberOfSkills(person, skillNamesToConsider);
	}
	
	public static int getNumberOfSkills(Person person) {
		return person.getNumberOfSkills();
	}
	
	public static int getNumberOfSkills(Person person, Set<String> skillNamesToConsider) {
		int numberOfSkills = 0;
		for (Skill skill: person.getSkillSet())
			if (skillNamesToConsider.contains(skill.getName()))
				++ numberOfSkills;
		return numberOfSkills;
	}
	
	public static double getTotalSkillRate(Person person) {
		return getTotalSkillRate(person, person.getSkillNameSet());
	}
	
	public static double getTotalSkillRate(Person person, Set<String> skillNamesToConsider) {
		double totalSkillRate = 0;
		for (Skill skill: person.getSkillSet()) {
			if (skillNamesToConsider.contains(skill.getName()))
				totalSkillRate += skill.getRate();
		}
		return totalSkillRate;
	}
	
}
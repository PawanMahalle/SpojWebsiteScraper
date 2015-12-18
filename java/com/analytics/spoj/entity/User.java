package com.analytics.spoj.entity;

import java.util.Set;

/**
 * This class defines User entity which holds Spoj user profile.
 * 
 * @author PawanMahalle
 *
 */
public class User {
	
	String name;
	
	/*
	 * Unique user name
	 */
	String username;
	String country;
	int rank;
	String institution;

	/*
	 * count of problems solved from classical problem category
	 */
	int classicalSolvedCount;
	/*
	 * score obtained by solving classical problems
	 */
	double classicalScore;

	/*
	 * count of problems solved from challenge problem category
	 */
	int challengeSolvedCount;
	/*
	 * score obtained by solving challenge problems
	 */
	double challengeScore;

	/*
	 * Set of classical problems solved.
	 */
	Set<Problem> classicalProblemsSolved;

	public User() {

	}

	public User(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public int getClassicalSolvedCount() {
		return classicalSolvedCount;
	}

	public void setClassicalSolvedCount(int classicalSolvedCount) {
		this.classicalSolvedCount = classicalSolvedCount;
	}

	public double getClassicalScore() {
		return classicalScore;
	}

	public void setClassicalScore(double classicalScore) {
		this.classicalScore = classicalScore;
	}

	public int getChallengeSolvedCount() {
		return challengeSolvedCount;
	}

	public void setChallengeSolvedCount(int challengeSolvedCount) {
		this.challengeSolvedCount = challengeSolvedCount;
	}

	public double getChallengeScore() {
		return challengeScore;
	}

	public void setChallengeScore(double challengeScore) {
		this.challengeScore = challengeScore;
	}

	public Set<Problem> getClassicalProblemsSolved() {
		return classicalProblemsSolved;
	}

	public void setClassicalProblemsSolved(Set<Problem> classicalProblemsSolved) {
		this.classicalProblemsSolved = classicalProblemsSolved;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public boolean equals(Object obj) {
		User u = (User) obj;

		if (this.getName().equals(u.getName()) && this.getCountry().equals(u.getCountry())
				&& this.getChallengeScore() == u.getChallengeScore()
				&& this.getChallengeScore() == u.getChallengeScore()
				&& this.getClassicalScore() == u.getClassicalScore()
				&& this.getChallengeSolvedCount() == u.getChallengeSolvedCount()
				&& this.getClassicalSolvedCount() == u.getClassicalSolvedCount()) {
			return true;
		}
		return false;

	}
}

package com.analytics.spoj.entity;

/**
 * This class defines Problem entity which holds details of problems available on Spoj.
 * 
 * @author PawanMahalle
 *
 */
public class Problem {
	
	/*
	 * unique code of problem
	 */
	String code;
	
	/*
	 * title of problem 
	 */
	String title;
	
	/*
	 * count of users who solved the problem
	 */
	int solvedByCount;
	
	/*
	 * Accuracy of solving the problem
	 */
	double accuracy;

	public Problem(){
		
	}
	
	/**
	 * Creates an instance using unique problem code 
	 * @param code
	 */
	public Problem(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSolvedByCount() {
		return solvedByCount;
	}

	public void setSolvedByCount(int solvedByCount) {
		this.solvedByCount = solvedByCount;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	@Override
	public String toString() {
		return code + ":" + title + "," + solvedByCount + "," + accuracy;
	}
}

package com.analytics.spoj.job;

import java.io.IOException;
import java.util.List;

import com.analytics.spoj.entity.Problem;
import com.analytics.spoj.entitymanager.ProblemManager;
import com.analytics.spoj.scrapper.SpojWebScrapper;

/**
 * This class scraps problem data from Spoj website and stores the data to database tables defined as per hibernate configurations.
 *  
 * @author PawanMahalle
 *
 */
public class ProblemDataPopulator {
	
	public static void main(String[] args) {
		
		//TODO: Define the upper limit in an adaptive way
		final int NO_OF_PROBLEM_PAGES = 65;
		final int OFFSET = 50;
		
		long t = System.currentTimeMillis();
		for(int offset = 0; offset <= NO_OF_PROBLEM_PAGES*OFFSET; offset+=OFFSET){
			try {
				List<Problem> problems = SpojWebScrapper.scrapProblemPage(offset);
				ProblemManager.addProblem(problems);
			} catch (IOException e) {
				//TODO: Add event logging functionality
				e.printStackTrace();
			}
		}
		System.out.println("All problem data updated in "+(System.currentTimeMillis() - t)+" seconds.");
	}
}

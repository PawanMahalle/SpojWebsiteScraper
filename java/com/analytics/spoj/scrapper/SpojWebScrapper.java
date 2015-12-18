package com.analytics.spoj.scrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.analytics.spoj.entity.Problem;
import com.analytics.spoj.entity.User;
import com.analytics.spoj.entitymanager.ProblemManager;
import com.analytics.spoj.entitymanager.UserManager;

/*
 * This class provides static methods to data from www.spoj.com web pages.
 * The following data can be collected using the methods provided
 * <ul>
 * <li>List of all users from <i>http://www.spoj.com/users/</i> 
 * <li>Basic information of a user and problems solved by the user</li>
 * <li>List of problems available on Spoj website</li>
 * </ul>
 */
public class SpojWebScrapper {

	private static final String PROBLEM_PAGE_URL = "http://www.spoj.com/problems/classical/start=";
	private static final String USER_PAGE_URL = "http://www.spoj.com/users/";
	private static final String USER_RANK_PAGE_URL = "http://www.spoj.com/ranks/users/start=";

	public static User scrapUserPage(String username) throws IOException {
		User user = UserManager.fetchUser(username);
		if (null == user) {
			user = new User(username);
		}
		return scrapUserPage(user);
	}

	public static User scrapUserPage(User user) throws IOException {

		Document doc;
		try {
			doc = Jsoup.connect(USER_PAGE_URL + user.getUsername()).timeout(10000).get();
		} catch (IOException e) {
			// TODO log into a file
			System.err.println("Error while scraping data from page: " + USER_PAGE_URL + user.getUsername());
			e.printStackTrace();
			throw e;
		}

		Elements classicalProblemCodes = doc.select("table").first().select("td");

		Set<Problem> problems = new HashSet();
		for (Element classicalProblemCode : classicalProblemCodes) {
			if (classicalProblemCode.text().isEmpty()) {
				continue;
			}
			Problem problem = ProblemManager.fetchProblem(classicalProblemCode.text().trim());
			if (null == problem) {
				problem = new Problem(classicalProblemCode.text().trim());
				problem.setSolvedByCount(-1);
				problem.setAccuracy(-1.0);
				ProblemManager.addProblem(problem);
			}
			problems.add(problem);
		}

		user.setClassicalProblemsSolved(problems);

		if (doc.getElementsByClass("col-md-3").first().select("p").size() > 3) {
			user.setInstitution(doc.getElementsByClass("col-md-3").first().select("p").get(3).text().trim());
		}

		return user;
	}

	public static List<User> scrapUserRankPage(int offset) throws IOException {

		Document doc;
		try {
			doc = Jsoup.connect(USER_RANK_PAGE_URL + offset).timeout(10000).get();
		} catch (IOException e) {
			// TODO log into a file
			System.err.println("Error while scraping data from page: " + USER_RANK_PAGE_URL + offset);
			e.printStackTrace();
			throw e;
		}

		Elements userInfoRows = doc.select("table").first().select("tr");

		List<User> users = new ArrayList<>();
		for (int i = 1; i < userInfoRows.size(); i++) {
			Elements userInfoData = userInfoRows.get(i).select("td");

			User user = new User(userInfoData.select("a").first().attr("href").split("/")[2]);
			user.setRank(Integer.parseInt(userInfoData.get(0).text().trim()));
			user.setCountry(userInfoData.select("img").first().attr("title"));
			user.setName(userInfoData.get(2).text());

			// extracting challenge score and problem solved using regex
			Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

			Matcher classicalDataMatcher = pattern.matcher(userInfoData.get(3).text());
			classicalDataMatcher.find();
			user.setClassicalScore(Double.parseDouble(classicalDataMatcher.group().trim()));
			classicalDataMatcher.find();
			user.setClassicalSolvedCount(Integer.parseInt(classicalDataMatcher.group().trim()));

			Matcher challengeDataMatcher = pattern.matcher(userInfoData.get(4).text());
			challengeDataMatcher.find();
			user.setChallengeScore(Double.parseDouble(challengeDataMatcher.group().trim()));
			challengeDataMatcher.find();
			user.setChallengeSolvedCount(Integer.parseInt(challengeDataMatcher.group().trim()));

			users.add(user);
		}

		return users;
	}

	public static List<Problem> scrapProblemPage(int offset) throws IOException {
		Document doc;
		try {
			doc = Jsoup.connect(PROBLEM_PAGE_URL + offset).timeout(10000).get();
		} catch (IOException e) {
			// TODO log into a file
			System.err.println("Error while scraping data from page: " + PROBLEM_PAGE_URL + offset);
			e.printStackTrace();
			throw e;
		}

		Elements problemInfoRows = doc.select("tbody").first().select("tr");

		// Wow Java7 is very considerate about the types :)
		List<Problem> problems = new ArrayList();

		for (Element problemInfo : problemInfoRows) {
			Elements problemInfoData = problemInfo.select("td");

			Problem problem = new Problem(problemInfoData.select("a").first().attr("href").split("/")[2]);
			problem.setTitle(problemInfoData.get(1).text());
			problem.setSolvedByCount(Integer.parseInt(problemInfoData.get(3).text().trim()));
			problem.setAccuracy(Double.parseDouble(problemInfoData.get(4).text().trim()));

			problems.add(problem);
		}

		return problems;
	}

}

package com.analytics.spoj.job;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.analytics.spoj.entity.User;
import com.analytics.spoj.entitymanager.UserManager;
import com.analytics.spoj.scrapper.SpojWebScrapper;
/**
 * This class scraps user profiles from Spoj website and stores the data to database tables defined as per hibernate configurations.
 * 
 * @author PawanMahalle
 *
 */
public class UserDataPopulator {
	
	public static void main(String[] args) {

		long t = System.currentTimeMillis();

		final int NO_OF_USER_PAGES = 454;
		
		List<User> existingUsers = UserManager.fetchUsers();
		System.out.println("No of users records available at present : " + existingUsers.size());
		Map<String, User> existingUsersMap = new HashMap<>();
		for (User user : existingUsers) {
			existingUsersMap.put(user.getUsername(), user);
		}

		// TODO Define the upper limit automatically
		for (int offset = 0; offset <= NO_OF_USER_PAGES; offset++) {
			try {
				System.out.println("Working on offset: " + (offset*100));
				List<User> latestUsers = SpojWebScrapper.scrapUserRankPage(offset*100);
				System.out.println("Users scrapped: " + latestUsers.size());
				for (User latestUser : latestUsers) {
					long time = System.currentTimeMillis();
					System.out.println("User under consideration: " + latestUser.getUsername());
					
					if((null != existingUsersMap.get(latestUser.getUsername())) && latestUser.equals(existingUsersMap.get(latestUser.getUsername()))){
						continue;
					}
					
					if (isUserPageScrappingRequired(latestUser, existingUsersMap)) {
						try{
						SpojWebScrapper.scrapUserPage(latestUser);
						}catch(IOException e){
							// TODO log into a file
							e.printStackTrace();
						}
					}

					UserManager.addUser(latestUser);
					System.out.println("Time taken by "+latestUser.getUsername()+" : "+(System.currentTimeMillis()-time));
				}
			} catch (IOException e) {
				// TODO log into a file
				e.printStackTrace();
			}
		}
		System.out.println("All problem data updated in " + (System.currentTimeMillis() - t) + " seconds.");
	}

	private static boolean isUserPageScrappingRequired(User user, Map<String, User> existingUsersMap) {
		if((null == existingUsersMap.get(user.getUsername())) || (existingUsersMap.get(user.getUsername()).getClassicalSolvedCount() != user.getClassicalSolvedCount())){
			return true;
		}
		return false;
	}
}

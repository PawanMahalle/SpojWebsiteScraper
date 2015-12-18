package com.analytics.spoj.entitymanager;

import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.analytics.spoj.entity.Problem;
import com.analytics.spoj.entity.User;

/**
 * This is an entity manager class for {@linkplain User} used by Hibernate
 * framework.
 * 
 * @author PawanMahalle
 *
 */
public class UserManager {

	static SessionFactory factory = null;

	static {
		factory = new Configuration().configure().buildSessionFactory();
	}

	/**
	 * adds user to <code>user</code> table from given list of user
	 * entities.
	 * 
	 * @param users
	 * list of {@linkplain User} entities to be stored in <i>user</i> table
	 */
	public static void addUser(List<User> users) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (User user : users) {
				session.saveOrUpdate(user);
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * adds a record to <i>user</i> table for given {@linkplain User} entity. 
	 * @param problem
	 * entity {@linkplain User} to be stored in database
	 */
	public static void addUser(User user) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			//TODO: Add event logging functionality
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/**
	 * retrieves {@linkplain User} entity given the username.
	 * @param username
	 * unique username for a user
	 * @return
	 * entity {@linkplain User} with given username
	 */
	public static User fetchUser(String username) {
		Session session = factory.openSession();
		List result = session.createCriteria(User.class).add(Restrictions.eq("username", username)).list();

		return (User) (result.isEmpty() ? null : result.get(0));
	}

	/**
	 * retrieves all {@linkplain User} entities present in <i>user</i> table.
	 * @return
	 * List of {@linkplain User} entities
	 */
	public static List fetchUsers() {
		Session session = factory.openSession();
		return session.createCriteria(User.class).list();
	}
}

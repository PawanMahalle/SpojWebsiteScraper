package com.analytics.spoj.entitymanager;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import com.analytics.spoj.entity.Problem;

/**
 * This is an entity manager class for {@linkplain Problem} used by Hibernate framework.
 * 
 * @author PawanMahalle
 *
 */
public class ProblemManager {

	static SessionFactory factory = null;

	static {
		factory = new Configuration().configure().buildSessionFactory();
	}

	/**
	 * adds problems to <code>problem</code> table from given list of problem entities. 
	 * @param problems
	 */
	public static void addProblem(List<Problem> problems) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (Problem problem : problems) {
				session.saveOrUpdate(problem);
			}
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
	 * adds a record to <i>problem</i> table for given list of {@linkplain Problem} entities. 
	 * @param problem
	 * entity {@linkplain Problem} to be stored in database
	 */
	public static void addProblem(Problem problem) {
		List<Problem> problems = new ArrayList<>();
		problems.add(problem);
		addProblem(problems);
	}

	/**
	 * retrieves {@linkplain Problem} entity given the problem code.
	 * @param code
	 * unique code of a problem
	 * @return
	 * entity {@linkplain Problem} with given code
	 */
	public static Problem fetchProblem(String code) {
		Session session = factory.openSession();
		List result = session.createCriteria(Problem.class).add(Restrictions.eq("code", code)).list();
		session.close();
		return (Problem) (result.isEmpty() ? null : result.get(0));
	}
}

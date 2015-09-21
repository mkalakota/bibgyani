package com.mk.fl.bibgyani;

import java.util.Random;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mk.fl.bibgyani.model.Question;
import com.mk.fl.bibgyani.util.HibernateUtil;

public class App {
	public static void main(String[] args) {
		System.out.println("Maven + Hibernate + MySQL");
		setup();
	}

	public static void setup() {
		// long[] amounts = { // question amounts
		// 4000, // first question
		// 8000, // second question
		// 12000, // third question
		// 1600, // fourth question
		// 20000, // fifth question
		// 26000, // sixth question
		// 32000, // seventh question
		// 38000, // eighth question
		// 44000, // ninth question
		// 50000, // tenth question
		// 60000, // eleventh question
		// 70000, // twelfth question
		// 80000, // thirteenth question
		// 90000, // fourteenth question
		// 100000 // fifteenth question
		// };
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = session.beginTransaction();

		String sql = "delete from Command";
		Query q = session.createQuery(sql);
		q.executeUpdate();
		session.flush();

		sql = "delete from Game";
		q = session.createQuery(sql);
		q.executeUpdate();
		session.flush();

		sql = "delete from Question";
		q = session.createQuery(sql);
		q.executeUpdate();
		session.flush();

		int level, qnum = 1;
		for (int j = 0; j < 40; j++) {
			level = -1;
			for (int i = 0; i < 15; i++, qnum++) {
				if (i % 5 == 0) {
					level++;
				}
				Question question = new Question(//
						"Question " + qnum//
						, "Option A"//
						, "Option B"//
						, "Option C"//
						, "Option D"//
						, "ప్రస్న " + qnum//
						, "సమాధానం ఎ"//
						, "సమాధానం బి"//
						, "సమాధానం సి"//
						, "సమాధానం డి"//
						, randInt(0, 3)//
						, level//
						, (short) randInt(10, 40)//
						);
				question.setAmountExtraAt((short) randInt(10, 30));
				session.save(question);
			}
		}
		tx.commit();
	}

	/**
	 * Returns a pseudo-random number between min and max, inclusive. The
	 * difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min
	 *            Minimum value
	 * @param max
	 *            Maximum value. Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}
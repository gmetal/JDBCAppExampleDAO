package gr.haec;

import java.sql.SQLException;
import java.util.List;

import gr.haec.db.DAOFactory;
import gr.haec.db.dao.Dao;
import gr.haec.model.Post;
import gr.haec.model.Term;
import gr.haec.model.User;

public class Main {

	public static void main(String[] args) {
		Dao<Post> postDao = null;
		Dao<User> userDao = null;
		Dao<Term> termDao = null;
		
		try {
			postDao = DAOFactory.getInstance().getPostsDao();
			userDao = DAOFactory.getInstance().getUserDao();
			termDao = DAOFactory.getInstance().getTermDao();
		} catch (SQLException e) {
			System.out.println("Could not connect to the database");
			e.printStackTrace();
			return;
		}
		
		int postsCount = postDao.countAll();
		System.out.println("WP Database contains: " + postsCount + " posts");
		int usersCount = userDao.countAll();
		System.out.println("WP Database contains: " + usersCount + " users");
		int termsCount = termDao.countAll();
		System.out.println("WP Database contains: " + termsCount + " terms");
		
		System.out.println("Acquiring all wordpress posts");
		List<Post> allPosts = postDao.getAll();

		if (allPosts != null) {
			for (int i = 0; i < allPosts.size(); i++) {
				Post currentPost = allPosts.get(i);
				System.out.println(currentPost);
			}
		} else {
			System.out.println("Could not retrieve wp posts");
		}

		System.out.println("Acquiring all wordpress users");
		List<User> allUsers = userDao.getAll();

		if (allUsers != null) {
			for (int i = 0; i < allUsers.size(); i++) {
				User currentUser = allUsers.get(i);
				System.out.println(currentUser);
			}
		} else {
			System.out.println("Could not retrieve wp users");
		}

		System.out.println("Acquiring all wordpress terms");
		List<Term> allTerms = termDao.getAll();

		if (allTerms != null) {
			for (int i = 0; i < allTerms.size(); i++) {
				Term currentTerm = allTerms.get(i);
				System.out.println(currentTerm);
			}
		} else {
			System.out.println("Could not retrieve wp terms");
		}

		System.out.println("Retrieving post by id");
		Post postById = postDao.get(4);

		if (postById != null) {
			System.out.println(postById);
		}
		
		System.out.println("Retrieving user by id");
		User userById = userDao.get(1);

		if (userById != null) {
			System.out.println(userById);
		}

		System.out.println("Retrieving term by id");
		Term termById = termDao.get(1);

		if (termById != null) {
			System.out.println(termById);
		}

		try {
			DAOFactory.getInstance().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not close the DAO Factory instance");
			e.printStackTrace();
		}
	}
}

package gr.haec;

import java.sql.SQLException;
import java.util.List;

import gr.haec.db.DAOFactory;
import gr.haec.db.dao.Dao;
import gr.haec.model.Category;
import gr.haec.model.Post;
import gr.haec.model.Tag;
import gr.haec.model.Term;
import gr.haec.model.User;

public class Main {

	public static void printPosts() {
		Dao<Post> postDao = null;

		try {
			postDao = DAOFactory.getInstance().getPostsDao();
		} catch (SQLException e) {
			System.out.println("Could not connect to the database");
			e.printStackTrace();
			return;
		}

		int postsCount = postDao.countAll();
		System.out.println("WP Database contains: " + postsCount + " posts");

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

		System.out.println("Retrieving post by id");
		Post postById = postDao.get(4);

		if (postById != null) {
			System.out.println(postById);
		}
	}

	public static void printUsers() {
		Dao<User> userDao = null;
		try {
			userDao = DAOFactory.getInstance().getUserDao();
		} catch (SQLException e) {
			System.out.println("Could not connect to the database");
			e.printStackTrace();
			return;
		}

		int usersCount = userDao.countAll();
		System.out.println("WP Database contains: " + usersCount + " users");

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

		System.out.println("Retrieving user by id");
		User userById = userDao.get(1);

		if (userById != null) {
			System.out.println(userById);
		}
	}

	public static void printTerms() {
		Dao<Term> termDao = null;

		try {
			termDao = DAOFactory.getInstance().getTermDao();
		} catch (SQLException e) {
			System.out.println("Could not connect to the database");
			e.printStackTrace();
			return;
		}

		int termsCount = termDao.countAll();
		System.out.println("WP Database contains: " + termsCount + " terms");

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

		System.out.println("Retrieving term by id");
		Term termById = termDao.get(1);

		if (termById != null) {
			System.out.println(termById);
		}
	}

	public static void printCategories() {

		Dao<Category> categoryDao = null;

		try {
			categoryDao = DAOFactory.getInstance().getCategoryDao();
		} catch (SQLException e) {
			System.out.println("Could not connect to the database");
			e.printStackTrace();
			return;
		}

		int categoryCount = categoryDao.countAll();
		System.out.println("WP Database contains: " + categoryCount + " categories");

		System.out.println("Acquiring all wordpress categories");
		List<Category> allCategories = categoryDao.getAll();

		if (allCategories != null) {
			for (int i = 0; i < allCategories.size(); i++) {
				Category currentCategory = allCategories.get(i);
				System.out.println(currentCategory);
			}
		} else {
			System.out.println("Could not retrieve wp categories");
		}

		System.out.println("Retrieving category by id");
		Category categoryById = categoryDao.get(1);

		if (categoryById != null) {
			System.out.println(categoryById);
		}
	}

	public static void printTags() {
		Dao<Tag> tagDao = null;

		try {
			tagDao = DAOFactory.getInstance().getTagDao();
		} catch (SQLException e) {
			System.out.println("Could not connect to the database");
			e.printStackTrace();
			return;
		}

		int tagCount = tagDao.countAll();
		System.out.println("WP Database contains: " + tagCount + " tag");

		System.out.println("Acquiring all wordpress tags");
		List<Tag> allTags = tagDao.getAll();

		if (allTags != null) {
			for (int i = 0; i < allTags.size(); i++) {
				Tag currentTags = allTags.get(i);
				System.out.println(currentTags);
			}
		} else {
			System.out.println("Could not retrieve wp Tags");
		}

		System.out.println("Retrieving tag by id");
		Tag tagById = tagDao.get(6);

		if (tagById != null) {
			System.out.println(tagById);
		}
	}

	public static void main(String[] args) {

		printPosts();
		printUsers();
		printTerms();
		printCategories();
		printTags();

		try {
			DAOFactory.getInstance().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not close the DAO Factory instance");
			e.printStackTrace();
		}
	}
}

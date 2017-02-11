package gr.haec;

import java.sql.SQLException;
import java.util.List;

import gr.haec.db.DAOFactory;
import gr.haec.db.dao.Dao;
import gr.haec.model.Post;

public class Main {

	public static void main(String[] args) {
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
		
		try {
			DAOFactory.getInstance().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not close the DAO Factory instance");
			e.printStackTrace();
		}
	}

}

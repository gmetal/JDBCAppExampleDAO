package gr.haec.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gr.haec.db.BaseDAO;
import gr.haec.model.Post;

// Post DAO class
public class PostDAO extends BaseDAO<Post> {

	// Attribute
	private PreparedStatement selectByIdStatement;
	private PreparedStatement selectAllStatement;
	private PreparedStatement countStatement;

	// Constructor
	public PostDAO(Connection conn) throws SQLException {
		super(conn);
		selectByIdStatement = dbConnection
				.prepareStatement("SELECT id, post_title, post_author FROM wp_posts WHERE id = ?;");
		selectAllStatement = dbConnection.prepareStatement("SELECT id, post_title, post_author FROM wp_posts;");
		countStatement = dbConnection.prepareStatement("SELECT count(*) FROM wp_posts;");
	}

	@Override
	public Post get(int id) {
		Post post = new Post();

		try {
			selectByIdStatement.setInt(1, id);
			selectByIdStatement.execute();
			ResultSet resultSet = selectByIdStatement.getResultSet();
			if (resultSet.first()) {
				post.setId(resultSet.getInt("id"));
				post.setPostAuthor(resultSet.getString("post_author"));
				post.setPostTitle(resultSet.getString("post_title"));
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Caught SQLException while executing get by id: " + id);
			e.printStackTrace();
			return null;
		}

		return post;
	}

	@Override
	public List<Post> getAll() {
		ResultSet resultSet;
		List<Post> objectList = new ArrayList<>();

		try {
			resultSet = selectAllStatement.executeQuery();

			while (resultSet.next()) {
				Post post = new Post();
				post.setId(resultSet.getInt("id"));
				post.setPostAuthor(resultSet.getString("post_author"));
				post.setPostTitle(resultSet.getString("post_title"));
				objectList.add(post);
			}

			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Caught SQLException while trying to retrieve all WP posts");
			e.printStackTrace();
			return null;
		}

		return objectList;
	}

	@Override
	public int countAll() {
		int count = 0;
		try {
			ResultSet resultSet = countStatement.executeQuery();
			if (resultSet.first()) {
				count = resultSet.getInt(1);
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Caught SQLException while counting WP posts");
			e.printStackTrace();
			return -1;
		}
		return count;
	}

	@Override
	public void close() {
		try {
			this.selectAllStatement.close();
			this.selectByIdStatement.close();
			this.countStatement.close();
		} catch (SQLException e) {
			System.out.println("Could not close the DAO statements");
			e.printStackTrace();
		}
	}
}

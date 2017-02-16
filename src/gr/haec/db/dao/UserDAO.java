package gr.haec.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gr.haec.db.BaseDAO;
import gr.haec.model.Post;
import gr.haec.model.User;

public class UserDAO extends BaseDAO<User> {

	private PreparedStatement selectByIdStatement;
	private PreparedStatement selectAllStatement;
	private PreparedStatement countStatement;
	
	public UserDAO(Connection conn) throws SQLException {
		super(conn);
		selectByIdStatement = dbConnection
				.prepareStatement("SELECT id, user_login, user_email, user_registered FROM wp_users WHERE id = ?;");
		selectAllStatement = dbConnection.prepareStatement("SELECT id, user_login, user_email, user_registered FROM wp_users;");
		countStatement = dbConnection.prepareStatement("SELECT count(*) FROM wp_users;");
	}

	@Override
	public User get(int id) {
		User user = new User();

		try {
			selectByIdStatement.setInt(1, id);
			selectByIdStatement.execute();
			ResultSet resultSet = selectByIdStatement.getResultSet();
			if (resultSet.first()) {
				user.setId(resultSet.getInt("id"));
				user.setUserEmail(resultSet.getString("user_email"));
				user.setUserLogin(resultSet.getString("user_login"));
				user.setUserRegistered(resultSet.getString("user_registered"));
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Caught SQLException while executing get by id: " + id);
			e.printStackTrace();
			return null;
		}

		return user;
	}

	@Override
	public List<User> getAll() {
		ResultSet resultSet;
		List<User> objectList = new ArrayList<>();

		try {
			resultSet = selectAllStatement.executeQuery();

			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setUserEmail(resultSet.getString("user_email"));
				user.setUserLogin(resultSet.getString("user_login"));
				user.setUserRegistered(resultSet.getString("user_registered"));
				objectList.add(user);
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

package gr.haec.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import gr.haec.db.dao.Dao;
import gr.haec.db.dao.PostDAO;
import gr.haec.model.Post;

// Factory class to retrieve all DAOs
public class DAOFactory {

	// Constants
	private static final String DB_URL = "jdbc:mysql://localhost/wordpress";
	private static final String POST_DAO_KEY = "post.dao";

	// Attributes
	private Connection dbConnection;
	private Map<String, BaseDAO> daoTable;

	// Singleton instance
	private static DAOFactory instance;

	// Constructor
	protected DAOFactory() throws SQLException {
		dbConnection = DriverManager.getConnection(DB_URL, "wordpress_u", "wordpress_p");
		daoTable = new HashMap<String, BaseDAO>();
	}

	public static DAOFactory getInstance() throws SQLException {
		if (instance == null) {
			instance = new DAOFactory();
		}

		return instance;
	}

	public Dao<Post> getPostsDao() throws SQLException {
		if (!daoTable.containsKey(POST_DAO_KEY)) {
			daoTable.put(POST_DAO_KEY, new PostDAO(dbConnection));
		}

		return (Dao<Post>) daoTable.get(POST_DAO_KEY);
	}

	public void close() {

		Collection<BaseDAO> daos = daoTable.values();
		Iterator<BaseDAO> daoIterator = daos.iterator();
		while (daoIterator.hasNext()) {
			daoIterator.next().close();
		}

		try {
			this.dbConnection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection to database");
			e.printStackTrace();
		}
	}
}

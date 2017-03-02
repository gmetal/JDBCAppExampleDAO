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
import gr.haec.db.dao.TermDAO;
import gr.haec.db.dao.UserDAO;
import gr.haec.model.Post;
import gr.haec.model.Term;
import gr.haec.model.User;

// Factory class to retrieve all DAOs
public class DAOFactory {

	// Constants
	private static final String DB_URL = "jdbc:mysql://localhost/wordpress";
	private static final String POST_DAO_KEY = "post.dao";
	private static final String USER_DAO_KEY = "user.dao";
	private static final String TERM_DAO_KEY = "term.dao";

	// Attributes
	private Connection dbConnection;
	private Map<String, BaseDAO> daoTable;

	// Singleton instance
	private static DAOFactory instance;

	// Constructor
	protected DAOFactory() throws SQLException {
		//dbConnection = DriverManager.getConnection(DB_URL, "wordpress_u", "wordpress_p");
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

	public Dao<User> getUserDao() throws SQLException {
		if (!daoTable.containsKey(USER_DAO_KEY)) {
			daoTable.put(USER_DAO_KEY, new UserDAO(dbConnection));
		}
		
		return (Dao<User>) daoTable.get(USER_DAO_KEY);
	}

	public Dao<Term> getTermDao() throws SQLException {
		if (!daoTable.containsKey(TERM_DAO_KEY)) {
			daoTable.put(TERM_DAO_KEY, new TermDAO(dbConnection));
		}
		
		return (Dao<Term>) daoTable.get(TERM_DAO_KEY);
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

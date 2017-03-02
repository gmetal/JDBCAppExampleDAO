package gr.haec.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gr.haec.db.BaseDAO;
import gr.haec.model.Term;

public class TermDAO extends BaseDAO<Term> {

	private PreparedStatement selectByIdStatement;
	private PreparedStatement selectAllStatement;
	private PreparedStatement countStatement;

	public TermDAO(Connection conn) throws SQLException {
		super(conn);
		selectByIdStatement = dbConnection
				.prepareStatement("SELECT term_id, name, slug, term_group FROM wp_terms WHERE term_id = ?;");
		selectAllStatement = dbConnection.prepareStatement("SELECT term_id, name, slug, term_group FROM wp_terms;");
		countStatement = dbConnection.prepareStatement("SELECT count(*) FROM wp_terms;");
	}

	@Override
	public Term get(int id) {
		Term term = new Term();

		try {
			selectByIdStatement.setInt(1, id);
			selectByIdStatement.execute();
			ResultSet resultSet = selectByIdStatement.getResultSet();
			if (resultSet.first()) {
				term.setId(resultSet.getInt("term_id"));
				term.setTermName(resultSet.getString("name"));
				term.setTermSlug(resultSet.getString("slug"));
				term.setTermGroup(resultSet.getInt("term_group"));
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Caught SQLException while executing get by id: " + id);
			e.printStackTrace();
			return null;
		}

		return term;
	}

	@Override
	public List<Term> getAll() {
		ResultSet resultSet;
		List<Term> objectList = new ArrayList<>();

		try {
			resultSet = selectAllStatement.executeQuery();

			while (resultSet.next()) {
				Term term = new Term();
				term.setId(resultSet.getInt("term_id"));
				term.setTermName(resultSet.getString("name"));
				term.setTermSlug(resultSet.getString("slug"));
				term.setTermGroup(resultSet.getInt("term_group"));
				objectList.add(term);
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
			System.out.println("Caught SQLException while counting WP terms");
			e.printStackTrace();
			return -1;
		}
		return count;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}

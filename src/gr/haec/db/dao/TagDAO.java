package gr.haec.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gr.haec.db.BaseDAO;
import gr.haec.model.Category;
import gr.haec.model.Tag;

public class TagDAO extends BaseDAO<Tag> {

	private PreparedStatement selectByIdStatement;
	private PreparedStatement selectAllStatement;
	private PreparedStatement countStatement;
	
	public TagDAO(Connection conn) throws SQLException {
		super(conn);
		
		selectByIdStatement = dbConnection
				.prepareStatement("SELECT terms.term_id, terms.name, terms.slug, terms.term_group FROM wp_terms terms"
						+ " JOIN wp_term_taxonomy terms_taxonomy ON terms.term_id = terms_taxonomy.term_id "
						+ "WHERE terms.term_id = ? AND terms_taxonomy.taxonomy = 'post_tag';");
		selectAllStatement = dbConnection
				.prepareStatement("SELECT terms.term_id, terms.name, terms.slug, terms.term_group FROM wp_terms terms"
						+ " JOIN wp_term_taxonomy terms_taxonomy ON terms.term_id = terms_taxonomy.term_id "
						+ "WHERE terms_taxonomy.taxonomy = 'post_tag';");
		countStatement = dbConnection.prepareStatement("SELECT count(*) FROM wp_terms terms"
				+ " JOIN wp_term_taxonomy terms_taxonomy ON terms.term_id = terms_taxonomy.term_id "
				+ "WHERE terms_taxonomy.taxonomy = 'post_tag'");
	}

	@Override
	public Tag get(int id) {
		Tag tag = new Tag();

		try {
			selectByIdStatement.setInt(1, id);
			selectByIdStatement.execute();
			ResultSet resultSet = selectByIdStatement.getResultSet();
			if (resultSet.first()) {
				tag.setId(resultSet.getInt("term_id"));
				tag.setTermName(resultSet.getString("name"));
				tag.setTermSlug(resultSet.getString("slug"));
				tag.setTermGroup(resultSet.getInt("term_group"));
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Caught SQLException while executing get by id: " + id);
			e.printStackTrace();
			return null;
		}

		return tag;

	}

	@Override
	public List<Tag> getAll() {
		ResultSet resultSet;
		List<Tag> objectList = new ArrayList<>();

		try {
			resultSet = selectAllStatement.executeQuery();

			while (resultSet.next()) {
				Tag tag = new Tag();
				tag.setId(resultSet.getInt("term_id"));
				tag.setTermName(resultSet.getString("name"));
				tag.setTermSlug(resultSet.getString("slug"));
				tag.setTermGroup(resultSet.getInt("term_group"));
				objectList.add(tag);
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

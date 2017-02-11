package gr.haec.db.dao;

import java.util.List;

// Main DAO interface
public interface Dao<T> {

	T get(int id);

	List<T> getAll();
	
	int countAll();
}

package jmyzik.librarymanager.query;

import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class AbstractQuery {

	protected EntityManagerHandler entityManagerHandler = EntityManagerHandler.INSTANCE;
	
	public void open() throws DatabaseUnavailableException {
		if (entityManagerHandler.isAvailable()) {
			entityManagerHandler.open();
		} else {
			throw new DatabaseUnavailableException();
		}
	}
	
	public void shutdown() {
		if (entityManagerHandler.isAvailable()) {
			entityManagerHandler.shutdown();
		}
	}
	
	public boolean restartConnection() {
		return entityManagerHandler.connectToDatabase();
	}
}
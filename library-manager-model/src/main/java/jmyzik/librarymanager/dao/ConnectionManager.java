package jmyzik.librarymanager.dao;

import jmyzik.librarymanager.model.EntityManagerHandler;

public class ConnectionManager {
	
	private EntityManagerHandler entityManagerHandler = EntityManagerHandler.INSTANCE;

	public void shutdown() {
		if (entityManagerHandler.isAvailable()) {
			entityManagerHandler.shutdown();
		}
	}
	
	public boolean restartConnection() {
		return entityManagerHandler.connectToDatabase();
	}
}

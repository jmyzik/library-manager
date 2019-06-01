package jmyzik.librarymanager.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.service.spi.ServiceException;

public enum EntityManagerHandler {
	INSTANCE;

	private EntityManagerFactory entityManagerFactory;
	private boolean isAvailable = false;

	private EntityManagerHandler() {
		connectToDatabase();
	}
	
	public EntityManager getNewEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public boolean connectToDatabase() {
		try {
			shutdown();
			entityManagerFactory = Persistence.createEntityManagerFactory("library-database");
			isAvailable = true;
		} catch (ServiceException e) {
			isAvailable = false;
		}
		return isAvailable;
	}
	
	public void shutdown() {
		if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
			entityManagerFactory.close();
		}
	}

	public boolean isAvailable() {
		return isAvailable;
	}
}
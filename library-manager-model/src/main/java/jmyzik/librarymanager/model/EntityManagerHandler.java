package jmyzik.librarymanager.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hibernate.service.spi.ServiceException;

public enum EntityManagerHandler {
	INSTANCE;

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;
	private boolean isAvailable = false;

	private EntityManagerHandler() {
		connectToDatabase();
	}

	public boolean connectToDatabase() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("library-database");
			entityManager = entityManagerFactory.createEntityManager();
			entityTransaction = entityManager.getTransaction();
			isAvailable = true;
		} catch (Exception e) {
			isAvailable = false;
		}
		return isAvailable;
	}
	
	public void open()  {
		if (!entityTransaction.isActive()) {
			entityTransaction.begin();
		}
	}

	public void shutdown() {					// TODO: call this when the application is closed!
		entityManager.close();
		entityManagerFactory.close();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public EntityTransaction getEntityTransaction() {
		return entityTransaction;
	}

	public boolean isAvailable() {
		return isAvailable;
	}
}
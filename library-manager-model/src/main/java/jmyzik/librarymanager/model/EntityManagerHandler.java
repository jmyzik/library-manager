package jmyzik.librarymanager.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public enum EntityManagerHandler {
	INSTANCE;

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("library-database");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();
	private EntityTransaction entityTransaction = entityManager.getTransaction();

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
}

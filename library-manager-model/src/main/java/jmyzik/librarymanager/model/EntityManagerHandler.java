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

	private EntityManagerHandler() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("library-database");
			entityManager = entityManagerFactory.createEntityManager();
			entityTransaction = entityManager.getTransaction();
		} catch (ServiceException e) {
			System.out.println("*********************************************");
			System.out.println("B£¥D!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("Message: " + e.getMessage());
			System.out.println("Cause: " + e.getCause());
			System.out.println("Class: " + e.getClass());
			System.out.println("*********************************************");
		}
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
}

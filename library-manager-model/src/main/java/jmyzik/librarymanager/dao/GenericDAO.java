package jmyzik.librarymanager.dao;

import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.model.EntityManagerHandler;

public abstract class GenericDAO {

	protected EntityManagerHandler entityManagerHandler = EntityManagerHandler.INSTANCE;
	
//	public void open() throws DatabaseUnavailableException {
//		if (entityManagerHandler.isAvailable()) {
//			entityManagerHandler.open();
//		} else {
//			throw new DatabaseUnavailableException();
//		}
//	}
}
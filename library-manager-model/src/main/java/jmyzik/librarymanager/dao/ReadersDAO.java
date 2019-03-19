package jmyzik.librarymanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class ReadersDAO extends GenericDAO {

	public void addReader(Reader reader) throws DatabaseUnavailableException {
		open();
		entityManagerHandler.getEntityManager().persist(reader);
		entityManagerHandler.getEntityTransaction().commit();
	}
	
	public List<Reader> getAllReaders() throws DatabaseUnavailableException {
		open();
		TypedQuery<Reader> query = entityManagerHandler.getEntityManager().createQuery("SELECT r FROM Reader r", Reader.class);
		List<Reader> readerList = query.getResultList();
		return readerList;
	}

	public boolean removeReader(Reader reader) throws DatabaseUnavailableException {
		open();
		if (entityManagerHandler.getEntityManager().contains(reader)) {
			entityManagerHandler.getEntityManager().remove(reader);
			entityManagerHandler.getEntityTransaction().commit();
			return true;
		}
		return false;
	}
}

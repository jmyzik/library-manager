package jmyzik.librarymanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class ReadersDAO extends GenericDAO {

	public void addReader(Reader reader, EntityManager em) {
		em.persist(reader);
	}
	
	public List<Reader> getAllReaders(EntityManager em) {
		TypedQuery<Reader> query = em.createQuery("SELECT r FROM Reader r", Reader.class);
		List<Reader> readerList = query.getResultList();
		return readerList;
	}

	public boolean removeReader(Reader reader, EntityManager em) {
		try {
			reader = em.find(Reader.class, reader.getId());
			em.remove(reader);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}

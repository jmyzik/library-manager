package jmyzik.librarymanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Reader;

public class ReadersDAO {

	public void addReader(Reader reader, EntityManager em) {
		em.persist(reader);
	}
	
	public List<Reader> getAllReaders(EntityManager em) {
		TypedQuery<Reader> query = em.createQuery("SELECT r FROM Reader r", Reader.class);
		List<Reader> readerList = query.getResultList();
		return readerList;
	}

	public void modifyReader(Reader newReader, EntityManager em) {
		em.merge(newReader);
	}

	public void removeReader(Reader reader, EntityManager em) {
		reader = em.find(Reader.class, reader.getId());
		em.remove(reader);
	}
}

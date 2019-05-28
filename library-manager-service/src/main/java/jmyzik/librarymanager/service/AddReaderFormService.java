package jmyzik.librarymanager.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jmyzik.librarymanager.dao.ReadersDAO;
import jmyzik.librarymanager.domain.Reader;

public class AddReaderFormService {
	
	ReadersDAO readersDAO;
	
	public AddReaderFormService() {
		readersDAO = new ReadersDAO();
	}

	public void addReader(Reader reader, EntityManager em) {
		EntityTransaction trans = em.getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
		readersDAO.addReader(reader, em);
		trans.commit();
	}
}

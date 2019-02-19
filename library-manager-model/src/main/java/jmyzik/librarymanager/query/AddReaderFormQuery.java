package jmyzik.librarymanager.query;

import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class AddReaderFormQuery extends AbstractQuery {

	public AddReaderFormQuery() {

	}
	
	public void addReader(Reader reader) {
		open();
		EntityManagerHandler.INSTANCE.getEntityManager().persist(reader);
		EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
	}
}

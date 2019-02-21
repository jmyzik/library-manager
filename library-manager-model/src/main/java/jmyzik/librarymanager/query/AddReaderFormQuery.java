package jmyzik.librarymanager.query;

import java.util.ArrayList;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class AddReaderFormQuery extends AbstractQuery {

	public AddReaderFormQuery() {

	}
	
	public void addReader(Reader reader) {
		if (!entityManagerHandler.isAvailable()) {
			return;
		}
		open();
		EntityManagerHandler.INSTANCE.getEntityManager().persist(reader);
		EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
	}
}

package jmyzik.librarymanager.query;

import java.util.ArrayList;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class AddReaderFormQuery extends AbstractQuery {

	public void addReader(Reader reader) throws DatabaseUnavailableException {
		open();
		entityManagerHandler.getEntityManager().persist(reader);
		entityManagerHandler.getEntityTransaction().commit();
	}
}

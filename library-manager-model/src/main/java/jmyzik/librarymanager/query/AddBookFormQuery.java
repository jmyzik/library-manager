package jmyzik.librarymanager.query;

import java.util.ArrayList;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class AddBookFormQuery extends AbstractQuery {

	public void addBook(Book book) throws DatabaseUnavailableException {
		open();
		entityManagerHandler.getEntityManager().persist(book);
		entityManagerHandler.getEntityTransaction().commit();
	}
}

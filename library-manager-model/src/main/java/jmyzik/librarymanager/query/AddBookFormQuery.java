package jmyzik.librarymanager.query;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class AddBookFormQuery extends AbstractQuery {

	public AddBookFormQuery() {

	}
	
	public void addBook(Book book) {
		open();
		EntityManagerHandler.INSTANCE.getEntityManager().persist(book);
		EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
	}
}

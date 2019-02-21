package jmyzik.librarymanager.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class MainFrameQuery extends AbstractQuery {
	
	public MainFrameQuery() {

	}
	
	public List<Book> getAllBooks() {
		if (!entityManagerHandler.isAvailable()) {
			return new ArrayList<Book>();
		}
		open();
		TypedQuery<Book> query = EntityManagerHandler.INSTANCE.getEntityManager().createQuery("SELECT b FROM Book b", Book.class);
		List<Book> bookList = query.getResultList();
		return bookList;
	}

	public List<Reader> getAllReaders() {
		if (!entityManagerHandler.isAvailable()) {
			return new ArrayList<Reader>();
		}
		open();
		TypedQuery<Reader> query = EntityManagerHandler.INSTANCE.getEntityManager().createQuery("SELECT r FROM Reader r", Reader.class);
		List<Reader> readerList = query.getResultList();
		return readerList;
	}

	public boolean removeReader(Reader reader) {
		open();
		EntityManagerHandler.INSTANCE.getEntityManager().remove(reader);
		EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
		return true;	// TODO: just a temporary fix, should return "false" if it was impossible to remove the reader
	}

	public boolean removeBook(Book book) {
		open();
		EntityManagerHandler.INSTANCE.getEntityManager().remove(book);
		EntityManagerHandler.INSTANCE.getEntityTransaction().commit();
		return true;	// TODO: just a temporary fix, should return "false" if it was impossible to remove the book
	}
}
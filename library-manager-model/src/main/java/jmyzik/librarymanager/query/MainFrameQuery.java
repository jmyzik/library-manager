package jmyzik.librarymanager.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.model.EntityManagerHandler;

public class MainFrameQuery extends AbstractQuery {
	
	public List<Book> getAllBooks() throws DatabaseUnavailableException {
		open();
		TypedQuery<Book> query = entityManagerHandler.getEntityManager().createQuery("SELECT b FROM Book b", Book.class);
		List<Book> bookList = query.getResultList();
		return bookList;
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

	public boolean removeBook(Book book) throws DatabaseUnavailableException {
		open();
		if (entityManagerHandler.getEntityManager().contains(book)) {
			entityManagerHandler.getEntityManager().remove(book);
			entityManagerHandler.getEntityTransaction().commit();
			return true;
		}
		return false;
	}

	public void addTransaction(BorrowTransaction transaction) throws DatabaseUnavailableException {
		open();
		entityManagerHandler.getEntityManager().persist(transaction);
		entityManagerHandler.getEntityTransaction().commit();
	}
}
package jmyzik.librarymanager.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class BooksDAO extends GenericDAO {

	public List<Book> getAllBooks() throws DatabaseUnavailableException {
		open();
		TypedQuery<Book> query = entityManagerHandler.getEntityManager().createQuery("SELECT b FROM Book b", Book.class);
		List<Book> bookList = query.getResultList();
		return bookList;
	}

	public void addBook(Book book) throws DatabaseUnavailableException {
		open();
		entityManagerHandler.getEntityManager().persist(book);
		entityManagerHandler.getEntityTransaction().commit();
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
	
	public boolean modifyBook(Book newBook) throws DatabaseUnavailableException {
		open();
		Book book = entityManagerHandler.getEntityManager().find(Book.class, newBook.getId());
		if (book == null) return false;
		book.setTitle(newBook.getTitle());
		book.setAuthor(newBook.getAuthor());
		book.setPublisher(newBook.getPublisher());
		book.setPublicationYear(newBook.getPublicationYear());
		book.setCopies(newBook.getCopies());
		entityManagerHandler.getEntityTransaction().commit();
		return true;
	}

	public void increaseCopies(Book book) throws DatabaseUnavailableException {
		open();
		int copies = book.getCopies();
		book.setCopies(++copies);
		entityManagerHandler.getEntityTransaction().commit();
	}
}
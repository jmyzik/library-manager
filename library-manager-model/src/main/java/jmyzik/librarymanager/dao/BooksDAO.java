package jmyzik.librarymanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class BooksDAO extends GenericDAO {

	public void addBook(Book book, EntityManager em) {
		em.persist(book);
	}
	
	public List<Book> getAllBooks(EntityManager em) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b", Book.class);
		List<Book> bookList = query.getResultList();
		return bookList;
	}

	public void modifyBook(Book newBook, EntityManager em) {
		em.merge(newBook);
	}

	public void removeBook(Book book, EntityManager em) {
		book = em.find(Book.class, book.getId());
		em.remove(book);
	}
}
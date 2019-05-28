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

	public boolean modifyBook(Book newBook, EntityManager em) {
		Book book = em.find(Book.class, newBook.getId());
		if (book == null) return false;
		book.setTitle(newBook.getTitle());
		book.setAuthor(newBook.getAuthor());
		book.setPublisher(newBook.getPublisher());
		book.setPublicationYear(newBook.getPublicationYear());
		book.setCopies(newBook.getCopies());
		return true;
	}

	public boolean removeBook(Book book, EntityManager em) {
		if (em.contains(book)) {			//TODO: It checks if the book is present in the persistence context, not in the db... Maybe replace it with a try block?
			em.remove(book);
			return true;
		}
		return false;
	}
}
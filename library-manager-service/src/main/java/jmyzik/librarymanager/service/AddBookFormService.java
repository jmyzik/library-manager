package jmyzik.librarymanager.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jmyzik.librarymanager.dao.BooksDAO;
import jmyzik.librarymanager.domain.Book;

public class AddBookFormService {
	
	BooksDAO booksDAO;
	
	public AddBookFormService() {
		booksDAO = new BooksDAO();
	}

	public void addBook(Book book, EntityManager em) {
		EntityTransaction trans = em.getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
		booksDAO.addBook(book, em);
		trans.commit();
	}
}

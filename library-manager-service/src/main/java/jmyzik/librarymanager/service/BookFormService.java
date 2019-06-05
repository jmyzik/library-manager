package jmyzik.librarymanager.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import jmyzik.librarymanager.dao.BooksDAO;
import jmyzik.librarymanager.domain.Book;

public class BookFormService {
	
	BooksDAO booksDAO;
	
	public BookFormService() {
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
	
	public void modifyBook(Book book, EntityManager em) {
		EntityTransaction trans = em.getTransaction();
		if (!trans.isActive()) {
			trans.begin();
		}
		booksDAO.modifyBook(book, em);
		trans.commit();		
	}
}

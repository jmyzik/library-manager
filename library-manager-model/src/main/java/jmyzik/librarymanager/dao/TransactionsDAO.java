package jmyzik.librarymanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;

public class TransactionsDAO {
	
	public void addTransaction(BorrowTransaction transaction, EntityManager em) {
		em.persist(transaction);
	}

	public List<BorrowTransaction> getAllTransactionsForReader(Reader reader, EntityManager em) {
		TypedQuery<BorrowTransaction> query = em.createQuery
				("SELECT b FROM BorrowTransaction b WHERE b.borrower.id = :readerId", BorrowTransaction.class);
		query.setParameter("readerId", reader.getId());
		List<BorrowTransaction> transactionList = query.getResultList();
		return transactionList;
	}

	public List<BorrowTransaction> getAllTransactionsForBook(Book book, EntityManager em) {
		TypedQuery<BorrowTransaction> query = em.createQuery
				("SELECT b FROM BorrowTransaction b WHERE b.book.id = :bookId", BorrowTransaction.class);
		query.setParameter("bookId", book.getId());
		List<BorrowTransaction> transactionList = query.getResultList();
		return transactionList;
	}

	public void removeTransaction(BorrowTransaction transaction, EntityManager em) {
		transaction = em.find(BorrowTransaction.class, transaction.getId());
		em.remove(transaction);
	}

	public void modifyTransaction(BorrowTransaction newTransaction, EntityManager em) {
		em.merge(newTransaction);
	}
}
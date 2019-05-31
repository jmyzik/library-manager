package jmyzik.librarymanager.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class TransactionsDAO extends GenericDAO {
	
	public void addTransaction(BorrowTransaction transaction, EntityManager em) {
		em.persist(transaction);
	}

	public List<BorrowTransaction> getAllTransactions(Reader reader, EntityManager em) {
		TypedQuery<BorrowTransaction> query = em.createQuery
				("SELECT b FROM BorrowTransaction b WHERE b.borrower.id = :readerId", BorrowTransaction.class);
		query.setParameter("readerId", reader.getId());
		List<BorrowTransaction> transactionList = query.getResultList();
		return transactionList;
	}

	public boolean removeTransaction(BorrowTransaction transaction, EntityManager em) {
		try {
			transaction = em.find(BorrowTransaction.class, transaction.getId());
			em.remove(transaction);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}
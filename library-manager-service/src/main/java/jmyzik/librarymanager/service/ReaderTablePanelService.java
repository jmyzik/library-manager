package jmyzik.librarymanager.service;

import java.util.List;

import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.query.ReaderTablePanelQuery;

public class ReaderTablePanelService {
	
	private ReaderTablePanelQuery query;
	
	public ReaderTablePanelService() {
		query = new ReaderTablePanelQuery();
	}
	
	public List<BorrowTransaction> getAllTransactions(Reader reader) throws DatabaseUnavailableException {
		return query.getAllTransactions(reader);
	}

	public void removeTransaction(BorrowTransaction transaction) throws DatabaseUnavailableException {
		query.removeTransaction(transaction);
		
	}
}
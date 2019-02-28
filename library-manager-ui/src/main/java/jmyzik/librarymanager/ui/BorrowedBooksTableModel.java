package jmyzik.librarymanager.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import jmyzik.librarymanager.domain.BorrowTransaction;

public class BorrowedBooksTableModel extends AbstractTableModel {

	private List<BorrowTransaction> transactionList;
	private String[] columnNames = { "Tytu³", "Autor", "Data wypo¿yczenia", "Data zwrotu" };

	public BorrowedBooksTableModel() {
		transactionList = new ArrayList<BorrowTransaction>();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return transactionList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BorrowTransaction transaction = transactionList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return transaction.getBook().getTitle();
		case 1:
			return transaction.getBook().getAuthor();
		case 2:
			return transaction.getBorrowDate();
		case 3:
			return transaction.getReturnDate();
		default:
			return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    if (transactionList.isEmpty()) {
	        return Object.class;
	    }
	    return getValueAt(0, columnIndex).getClass();
	}

	public void setTransactionList(List<BorrowTransaction> transactionList) {
		this.transactionList = transactionList;
	}
	
	public BorrowTransaction getTransaction (int row) {
		return transactionList.get(row);
	}
}
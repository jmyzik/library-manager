package jmyzik.librarymanager.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import jmyzik.librarymanager.domain.BorrowTransaction;

public class BorrowersTableModel extends AbstractTableModel {

	private List<BorrowTransaction> transactionList;
	private String[] columnNames = { "Czytelnik", "Data wypo¿yczenia", "Data zwrotu" };

	public BorrowersTableModel() {
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
			return transaction.getBorrower().toString();
		case 1:
			return transaction.getBorrowDate();
		case 2:
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
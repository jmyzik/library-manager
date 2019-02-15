package jmyzik.librarymanager.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import jmyzik.librarymanager.domain.Reader;

public class ReaderTableModel extends AbstractTableModel {

	private List<Reader> readerList;
	private String[] columnNames = { "Id", "Imiê", "Nazwisko", "Adres" };

	public ReaderTableModel() {
		readerList = new ArrayList<Reader>();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return readerList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Reader reader = readerList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return reader.getId();
		case 1:
			return reader.getFirstName();
		case 2:
			return reader.getLastName();
		case 3:
			return reader.getAddress();
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
	    if (readerList.isEmpty()) {
	        return Object.class;
	    }
	    return getValueAt(0, columnIndex).getClass();
	}

	public void setReaderList(List<Reader> readerList) {
		this.readerList = readerList;
	}
	
	public Reader getReader(int row) {
		return readerList.get(row);
	}
}
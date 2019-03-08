package jmyzik.librarymanager.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import jmyzik.librarymanager.domain.Book;

public class BookTableModel extends AbstractTableModel {

	private List<Book> bookList;
	private String[] columnNames = { "Id", "Tytu³", "Autor", "Wydawnictwo", "Rok publikacji", "Liczba egzemplarzy" };

	public BookTableModel() {
		bookList = new ArrayList<Book>();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return bookList.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Book book = bookList.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return book.getId();
		case 1:
			return book.getTitle();
		case 2:
			return book.getAuthor();
		case 3:
			return book.getPublisher();
		case 4:
			return book.getPublicationYear();
		case 5:
			return book.getCopies();
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
	    if (bookList.isEmpty()) {
	        return Object.class;
	    }
	    return getValueAt(0, columnIndex).getClass();
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
	public Book getBook(int row) {
		return bookList.get(row);
	}
}
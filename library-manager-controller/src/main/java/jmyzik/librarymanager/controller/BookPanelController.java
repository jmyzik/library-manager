package jmyzik.librarymanager.controller;

import java.util.List;

import javax.swing.JTable;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.ui.BookPanel;
import jmyzik.librarymanager.ui.BookTableModel;

public class BookPanelController {
	
	private BookTableModel tableModel;
	private JTable table;
	
	public BookPanelController(BookPanel bookPanel) {
		tableModel = bookPanel.getTableModel();
		table = bookPanel.getTable();
	}
	
	public void displayBooks(List<Book> bookList) {
		tableModel.setBookList(bookList);
		tableModel.fireTableDataChanged();
	}

	public Book getSelectedBook() {
		int row = table.getSelectedRow();
		if (row == -1) return null;
		row = table.convertRowIndexToModel(row);
		return tableModel.getBook(row);
	}
}

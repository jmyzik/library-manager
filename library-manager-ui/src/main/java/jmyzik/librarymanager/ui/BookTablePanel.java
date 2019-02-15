package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import jmyzik.librarymanager.domain.Book;

public class BookTablePanel extends JPanel {

	private BookTableModel tableModel;
	private JTable table;

	public BookTablePanel() {
		initalizeVariables();
		setUpTable();
		constructLayout();
	}

	private void initalizeVariables() {
		tableModel = new BookTableModel();
		table = new JTable();
	}

	private void setUpTable() {
		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
	}
	
	private void constructLayout() {
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void displayBooks(List<Book> bookList) {
		tableModel.setBookList(bookList);
		tableModel.fireTableDataChanged();
	}

	public Book getSelectedBook() {
		int row = table.getSelectedRow();			// TODO what if the table is empty...?
		if (row == -1) return null;
		row = table.convertRowIndexToModel(row);
		return tableModel.getBook(row);
	}
}
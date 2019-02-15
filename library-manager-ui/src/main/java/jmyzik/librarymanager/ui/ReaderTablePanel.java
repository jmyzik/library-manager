package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.Reader;

public class ReaderTablePanel extends JPanel {

	private ReaderTableModel readerTableModel;
	private JTable table;

	public ReaderTablePanel() {
		initalizeVariables();
		setUpTable();
		constructLayout();
	}

	private void initalizeVariables() {
		readerTableModel = new ReaderTableModel();
		table = new JTable();
	}
	
	private void setUpTable() {
		table.setModel(readerTableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
	}

	private void constructLayout() {
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}
	
	public void displayReaders(List<Reader> readerList) {
		readerTableModel.setReaderList(readerList);
		readerTableModel.fireTableDataChanged();
	}
	
	public Reader getSelectedReader() {
		int row = table.getSelectedRow();			// TODO what if the table is empty...?
		if (row == -1) return null;
		row = table.convertRowIndexToModel(row);
		return readerTableModel.getReader(row);
	}
}
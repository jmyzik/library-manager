package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.ReaderTablePanelService;

public class ReaderTablePanel extends JPanel {

	private ReaderTableModel readerTableModel;
	private BorrowedBooksTableModel borrowedBooksTableModel;
	private JTable readerTable;
	private JPanel detailsPanel;
	private JLabel nameLabel;
	private JTable borrowedBooksTable;
	private JPanel buttonPanel;
	private JButton editButton;
	private JButton borrowButton;
	private JButton returnButton;
	private ReaderTablePanelService readerTablePanelService;

	public ReaderTablePanel() {
		initalizeVariables();
		setUpReaderTable();
		setUpBorrowedBooksTable();
		constructLayout();
		addListeners();
	}

	private void initalizeVariables() {
		readerTableModel = new ReaderTableModel();
		borrowedBooksTableModel = new BorrowedBooksTableModel();
		readerTable = new JTable();		
		detailsPanel = new JPanel();
		nameLabel = new JLabel("Imiê i nazwisko");
		borrowedBooksTable = new JTable();
		buttonPanel = new JPanel();
		editButton = new JButton("Edytuj dane");
		borrowButton = new JButton("Wypo¿ycz ksi¹¿kê");
		returnButton = new JButton("Zwróæ ksi¹¿kê");
		
		readerTablePanelService = new ReaderTablePanelService();
	}
	
	private void setUpReaderTable() {
		readerTable.setModel(readerTableModel);
		readerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		readerTable.setFillsViewportHeight(true);
		readerTable.setAutoCreateRowSorter(true);
		setReaderTableColumnWidths();
	}
	
	private void setUpBorrowedBooksTable() {
		borrowedBooksTable.setModel(borrowedBooksTableModel);
		borrowedBooksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		borrowedBooksTable.setFillsViewportHeight(true);
		borrowedBooksTable.setAutoCreateRowSorter(true);
//		setBorrowedBooksTableTableColumnWidths();
	}

	private void setReaderTableColumnWidths() {
		int[] widths = { 40, 100, 140, 320 };
		for (int i = 0; i < widths.length; i++) {
			readerTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);			
		}
	}
	
	private void constructLayout() {
		setLayout(new GridLayout(2, 1, 0, 10));
		add(new JScrollPane(readerTable));
		
		detailsPanel.setLayout(new BorderLayout(10, 5));
		detailsPanel.add(nameLabel, BorderLayout.PAGE_START);
		detailsPanel.add(new JScrollPane(borrowedBooksTable), BorderLayout.CENTER);
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 10));
		buttonPanel.add(editButton);
		buttonPanel.add(borrowButton);
		buttonPanel.add(returnButton);
		detailsPanel.add(buttonPanel, BorderLayout.LINE_END);
		add(detailsPanel);
	}
	
	public void addListeners() {
		readerTable.getSelectionModel().addListSelectionListener(e -> readerSelected(e));
		returnButton.addActionListener(e -> returnSelectedBook());
	}
	
	private void readerSelected(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			updateBorrowedBooksTable();
		}		
	}
	
	public void displayReaders(List<Reader> readerList) {
		readerTableModel.setReaderList(readerList);
		readerTableModel.fireTableDataChanged();
	}
	
	public void displayTransactions(List<BorrowTransaction> transactionList) {
		borrowedBooksTableModel.setTransactionList(transactionList);
		borrowedBooksTableModel.fireTableDataChanged();		
	}
	
	public Reader getSelectedReader() {
		int row = readerTable.getSelectedRow();
		if (row == -1) return null;
		row = readerTable.convertRowIndexToModel(row);
		return readerTableModel.getReader(row);
	}

	private BorrowTransaction getSelectedTransaction() {
		int row = borrowedBooksTable.getSelectedRow();
		if (row == -1) return null;
		row = borrowedBooksTable.convertRowIndexToModel(row);
		return borrowedBooksTableModel.getTransaction(row);
	}

	private void showDatabaseUnavailableMessage() {
		JOptionPane.showMessageDialog(this,
				"Wyst¹pi³ problem z baz¹ danych.\n" +
						"Zamknij wszystkie aplikacje, które mog¹ korzystaæ z bazy, i kliknij przycisk \"Odœwie¿\"",
				"B³¹d",
				JOptionPane.ERROR_MESSAGE);			
	}
	
	private void returnSelectedBook() {
		BorrowTransaction transaction = getSelectedTransaction();
		if (transaction == null) {
			JOptionPane.showMessageDialog(this,
					"Zaznacz ksi¹¿kê, któr¹ chcesz zwróciæ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			readerTablePanelService.removeTransaction(transaction);
		} catch (DatabaseUnavailableException e) {
			showDatabaseUnavailableMessage();
		}
		updateBorrowedBooksTable();
	}
	
	private void updateBorrowedBooksTable() {
		Reader reader = getSelectedReader();
		if (reader == null) return;
		try {
			List<BorrowTransaction> transactionList = readerTablePanelService.getAllTransactions(reader);
			displayTransactions(transactionList);
		} catch (DatabaseUnavailableException e1) {
			showDatabaseUnavailableMessage();
		}
	}
}
package jmyzik.librarymanager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.ReaderPanelService;
import jmyzik.librarymanager.ui.BorrowedBooksTableModel;
import jmyzik.librarymanager.ui.ReaderPanel;
import jmyzik.librarymanager.ui.ReaderTableModel;

public class ReaderPanelController {

	private ReaderPanel readerPanel;
	private ReaderPanelService readerPanelService;
	private BookTableChangedCallback bookTableChangedCallback;

	private ReaderTableModel readerTableModel;
	private BorrowedBooksTableModel borrowedBooksTableModel;
	private JTable readerTable;
	private JTable borrowedBooksTable;
	private JLabel nameLabel;
	private JButton returnButton;
	
	public ReaderPanelController(ReaderPanel readerPanel) {
		this.readerPanel = readerPanel;
		readerPanelService = new ReaderPanelService();
		
		readerTableModel = readerPanel.getReaderTableModel();
		borrowedBooksTableModel = readerPanel.getBorrowedBooksTableModel();
		readerTable = readerPanel.getReaderTable();
		borrowedBooksTable = readerPanel.getBorrowedBooksTable();
		nameLabel = readerPanel.getNameLabel();
		returnButton = readerPanel.getReturnButton();

		addListeners();
	}
	
	private void addListeners() {
		readerTable.getSelectionModel().addListSelectionListener(e -> readerSelected(e));
		returnButton.addActionListener(e -> returnSelectedBook());
	}
	
	public void setBookTableChangedCallback(BookTableChangedCallback bookTableChangedCallback) {
		this.bookTableChangedCallback = bookTableChangedCallback;
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
	
	private void displayTransactions(List<BorrowTransaction> transactionList) {
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
		JOptionPane.showMessageDialog(readerPanel,
				"Wyst¹pi³ problem z baz¹ danych.\n" +
						"Zamknij wszystkie aplikacje, które mog¹ korzystaæ z bazy, i kliknij przycisk \"Odœwie¿\"",
				"B³¹d",
				JOptionPane.ERROR_MESSAGE);			
	}
	
	private void returnSelectedBook() {
		BorrowTransaction transaction = getSelectedTransaction();
		
		if (transaction == null) {
			JOptionPane.showMessageDialog(readerPanel,
					"Zaznacz ksi¹¿kê, któr¹ chcesz zwróciæ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		try {
			readerPanelService.returnBook(transaction);
			updateBorrowedBooksTable();
			bookTableChangedCallback.bookTableChanged();
		} catch (DatabaseUnavailableException e) {
			showDatabaseUnavailableMessage();
		}
	}
	
	public void updateBorrowedBooksTable() {
		Reader reader = getSelectedReader();
		if (reader == null) {
			nameLabel.setText("");
			displayTransactions(new ArrayList<BorrowTransaction>());
			return;
		}
		nameLabel.setText(reader.toString());
		try {
			List<BorrowTransaction> transactionList = readerPanelService.getAllTransactions(reader);
			displayTransactions(transactionList);
		} catch (DatabaseUnavailableException e1) {
			showDatabaseUnavailableMessage();
		}
	}
}
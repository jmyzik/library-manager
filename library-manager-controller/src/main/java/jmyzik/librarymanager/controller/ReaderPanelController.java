package jmyzik.librarymanager.controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
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
	private JButton borrowButton;
	private JButton returnButton;
	
	public ReaderPanelController(ReaderPanel readerPanel) {
		this.readerPanel = readerPanel;
		readerPanelService = new ReaderPanelService();
		
		readerTableModel = readerPanel.getReaderTableModel();
		borrowedBooksTableModel = readerPanel.getBorrowedBooksTableModel();
		readerTable = readerPanel.getReaderTable();
		borrowedBooksTable = readerPanel.getBorrowedBooksTable();
		nameLabel = readerPanel.getNameLabel();
		borrowButton = readerPanel.getBorrowButton();
		returnButton = readerPanel.getReturnButton();
	}
	
	public void addListeners(ActionListener listener) {
		readerTable.getSelectionModel().addListSelectionListener(e -> readerSelected(e));
		returnButton.addActionListener(listener);
		borrowButton.addActionListener(listener);
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
				"Wyst�pi� problem z baz� danych.\n" +
						"Zamknij wszystkie aplikacje, kt�re mog� korzysta� z bazy, i kliknij przycisk \"Od�wie�\"",
				"B��d",
				JOptionPane.ERROR_MESSAGE);			
	}
	
	public void returnSelectedBook() {
		BorrowTransaction transaction = getSelectedTransaction();
		
		if (transaction == null) {
			JOptionPane.showMessageDialog(readerPanel,
					"Zaznacz ksi��k�, kt�r� chcesz zwr�ci�",
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
			nameLabel.setText("Imi� i nazwisko");
			displayTransactions(new ArrayList<BorrowTransaction>());
			return;
		}
		
		SwingWorker<List<BorrowTransaction>, Void> worker = new SwingWorker<List<BorrowTransaction>, Void>() {
			@Override
			protected List<BorrowTransaction> doInBackground() throws Exception {
				List<BorrowTransaction> transactionList = new ArrayList<BorrowTransaction>();
				try {
					transactionList = readerPanelService.getAllTransactions(reader);
				} catch (DatabaseUnavailableException e) {
					showDatabaseUnavailableMessage();
				}
				return transactionList;
			}

			@Override
			protected void done() {
				try {
					displayTransactions(get());
				} catch (InterruptedException | ExecutionException e) {
					JOptionPane.showMessageDialog(readerPanel,
							"Wyst�pi� b��d, nie uda�o si� wy�wietli� listy wypo�yczonych ksi��ek.",
							"B��d",
							JOptionPane.ERROR_MESSAGE);
				}
				nameLabel.setText(reader.toString());
			}			
		};

		worker.execute();
	}
}
package jmyzik.librarymanager.controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.model.EntityManagerHandler;
import jmyzik.librarymanager.service.BookPanelService;
import jmyzik.librarymanager.ui.BookPanel;
import jmyzik.librarymanager.ui.BookTableModel;
import jmyzik.librarymanager.ui.BorrowersTableModel;

public class BookPanelController {
	
	private BookPanel bookPanel;
	private BookPanelService bookPanelService;
	private BookTableChangedCallback bookTableChangedCallback;
	
	private BookTableModel bookTableModel;
	private BorrowersTableModel borrowersTableModel;
	private JTable bookTable;
	private JTable borrowersTable;
	private JLabel titleLabel;
	private JButton editButton;
	private JButton borrowButton;
	private JButton returnButton;

	
	public BookPanelController(BookPanel bookPanel) {
		this.bookPanel = bookPanel;
		bookPanelService = new BookPanelService();

		bookTableModel = bookPanel.getBookTableModel();
		borrowersTableModel = bookPanel.getBorrowersTableModel();
		bookTable = bookPanel.getBookTable();
		borrowersTable = bookPanel.getBorrowersTable();
		titleLabel = bookPanel.getTitleLabel();
		editButton = bookPanel.getEditButton();
		borrowButton = bookPanel.getBorrowButton();
		returnButton = bookPanel.getReturnButton();
	}
	
	public void addListeners(ActionListener listener) {
		bookTable.getSelectionModel().addListSelectionListener(e -> bookSelected(e));
		editButton.addActionListener(listener);
		returnButton.addActionListener(e -> returnSelectedBook());
		borrowButton.addActionListener(listener);
	}
	
	public void setBookTableChangedCallback(BookTableChangedCallback bookTableChangedCallback) {
		this.bookTableChangedCallback = bookTableChangedCallback;
	}	

	private void bookSelected(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()) {
			updateBorrowersTable();
		}		
	}
	
	public void displayBooks(List<Book> bookList) {
		bookTableModel.setBookList(bookList);
		bookTableModel.fireTableDataChanged();
	}

	private void displayTransactions(List<BorrowTransaction> transactionList) {
		borrowersTableModel.setTransactionList(transactionList);
		borrowersTableModel.fireTableDataChanged();		
	}
	
	public Book getSelectedBook() {
		int row = bookTable.getSelectedRow();
		if (row == -1) return null;
		row = bookTable.convertRowIndexToModel(row);
		return bookTableModel.getBook(row);
	}
	
	private BorrowTransaction getSelectedTransaction() {
		int row = borrowersTable.getSelectedRow();
		if (row == -1) return null;
		row = borrowersTable.convertRowIndexToModel(row);
		return borrowersTableModel.getTransaction(row);
	}
	
	private void showDatabaseUnavailableMessage() {
		JOptionPane.showMessageDialog(bookPanel,
				"Wyst¹pi³ problem z baz¹ danych.\n" +
						"Zamknij wszystkie aplikacje, które mog¹ korzystaæ z bazy, i kliknij przycisk \"Odœwie¿\"",
				"B³¹d",
				JOptionPane.ERROR_MESSAGE);			
	}
	
	public void returnSelectedBook() {
		BorrowTransaction transaction = getSelectedTransaction();
		
		if (transaction == null) {
			JOptionPane.showMessageDialog(bookPanel,
					"Zaznacz czytelnika, który zwraca ksi¹¿kê",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		boolean success = false;
		EntityManager em = null;
		try {
			em = EntityManagerHandler.INSTANCE.getNewEntityManager();
			success = bookPanelService.returnBook(transaction, em);
		} catch (IllegalStateException e) {
			success = false;
			showDatabaseUnavailableMessage();
		} catch (Exception e) {
			success = false;
		} finally {
			if (em != null) {
				em.close();
			}
		}
		if (success) {
			updateBorrowersTable();
			bookTableChangedCallback.bookTableChanged();
		} else {
			JOptionPane.showMessageDialog(bookPanel,
					"Wyst¹pi³ b³¹d, nie uda³o siê zwróciæ ksi¹¿ki.",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateBorrowersTable() {
		Book book = getSelectedBook();
		if (book == null) {
			titleLabel.setText("Tytu³");
			displayTransactions(new ArrayList<BorrowTransaction>());
			return;
		}
		
		SwingWorker<List<BorrowTransaction>, Void> worker = new SwingWorker<List<BorrowTransaction>, Void>() {
			EntityManager em = null;
			
			@Override
			protected List<BorrowTransaction> doInBackground() throws Exception {
				List<BorrowTransaction> transactionList = new ArrayList<BorrowTransaction>();
				try {
					em = EntityManagerHandler.INSTANCE.getNewEntityManager();
					transactionList = bookPanelService.getAllTransactions(book, em);
				} catch (IllegalStateException e) {
					showDatabaseUnavailableMessage();
				}
				return transactionList;
			}

			@Override
			protected void done() {
				try {
					displayTransactions(get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(bookPanel,
							"Wyst¹pi³ b³¹d, nie uda³o siê wyœwietliæ listy wypo¿yczeñ.",
							"B³¹d",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					if (em != null) {
						em.close();
					}
				}
				titleLabel.setText(book.toString());
			}			
		};

		worker.execute();
	}
}
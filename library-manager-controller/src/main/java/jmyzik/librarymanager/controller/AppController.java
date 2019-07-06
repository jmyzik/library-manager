package jmyzik.librarymanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.persistence.EntityManager;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.callbacks.ReaderTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.enums.Actions;
import jmyzik.librarymanager.model.EntityManagerHandler;
import jmyzik.librarymanager.service.MainFrameService;
import jmyzik.librarymanager.ui.MainFrame;

public class AppController implements ActionListener, BookTableChangedCallback, ReaderTableChangedCallback {
	
	private MainFrame mainFrame;
	
	private MainFrameService mainFrameService;
	
	private AppMenuBarController appMenuBarController;
	private BookPanelController bookPanelController;
	private ReaderPanelController readerPanelController;
	private AddBookFormController addBookFormController;
	private AddReaderFormController addReaderFormController;
	private EditBookFormController editBookFormController;
	private EditReaderFormController editReaderFormController;
	
	private JButton refreshButton;
	
	public AppController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		mainFrameService = new MainFrameService();
		appMenuBarController = new AppMenuBarController(mainFrame.getAppMenuBar());
		bookPanelController = new BookPanelController(mainFrame.getBookPanel());
		readerPanelController = new ReaderPanelController(mainFrame.getReaderPanel());
		addBookFormController = new AddBookFormController(mainFrame.getAddBookForm());
		addReaderFormController = new AddReaderFormController(mainFrame.getAddReaderForm());
		editBookFormController = new EditBookFormController(mainFrame.getEditBookForm());
		editReaderFormController = new EditReaderFormController(mainFrame.getEditReaderForm());
		
		refreshButton = mainFrame.getRefreshButton();
		
		addListeners();
		setCallbacks();
	}
	
	private void addListeners() {
		appMenuBarController.addListeners(this);
		bookPanelController.addListeners(this);
		readerPanelController.addListeners(this);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeApp();
			}
		});
		refreshButton.addActionListener(e -> connectAndUpdate());
	}

	private void setCallbacks() {
		addBookFormController.setBookTableChangedCallback(this);
		editBookFormController.setBookTableChangedCallback(this);
		addReaderFormController.setReaderTableChangedCallback(this);
		editReaderFormController.setReaderTableChangedCallback(this);
		readerPanelController.setBookTableChangedCallback(this);
		bookPanelController.setBookTableChangedCallback(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(Actions.CLOSE_APP.actionName())) {
			closeApp();
		} else if (command.equals(Actions.ADD_BOOK.actionName())) {
			addBookFormController.displayForm();
		} else if (command.equals(Actions.EDIT_BOOK.actionName())) {
			editSelectedBook();
		} else if (command.equals(Actions.REMOVE_BOOK.actionName())) {
			removeSelectedBook();
		} else if (command.equals(Actions.ADD_READER.actionName())) {
			addReaderFormController.displayForm();
		} else if (command.equals(Actions.EDIT_READER.actionName())) {
			editSelectedReader();
		} else if (command.equals(Actions.REMOVE_READER.actionName())) {
			removeSelectedReader();
		} else if (command.equals(Actions.BORROW_BOOK.actionName())) {
			borrowBook();
		}
	}
	
	@Override
	public void bookTableChanged() {
		updateBookTable();
	}

	@Override
	public void readerTableChanged() {
		updateReaderTable();
	}	

	private void updateBookTable() {
		SwingWorker<List<Book>, Void> worker = new SwingWorker<List<Book>, Void>() {
			EntityManager em = null;
			
			@Override
			protected List<Book> doInBackground() throws Exception {
				List<Book> bookList = new ArrayList<Book>();
				try {
					em = EntityManagerHandler.INSTANCE.getNewEntityManager();
					bookList = mainFrameService.getAllBooks(em);
				} catch (IllegalStateException e) {
					showDatabaseUnavailableMessage();
				}
				return bookList;
			}

			@Override
			protected void done() {
				try {
					bookPanelController.displayBooks(get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainFrame,
							"Wyst¹pi³ b³¹d, nie uda³o siê wyœwietliæ listy ksi¹¿ek.",
							"B³¹d",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					if (em != null) {
						em.close();
					}
				}
			}
		};

		worker.execute();
	}

	private void updateReaderTable() {
		SwingWorker<List<Reader>, Void> worker = new SwingWorker<List<Reader>, Void>() {
			EntityManager em = null;

			@Override
			protected List<Reader> doInBackground() throws Exception {
				List<Reader> readerList = new ArrayList<Reader>();
				try {
					em = EntityManagerHandler.INSTANCE.getNewEntityManager();
					readerList = mainFrameService.getAllReaders(em);
				} catch (IllegalStateException e) {
					showDatabaseUnavailableMessage();
				}
				return readerList;
			}

			@Override
			protected void done() {
				try {
					readerPanelController.displayReaders(get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(mainFrame,
							"Wyst¹pi³ b³¹d, nie uda³o siê wyœwietliæ listy czytelników.",
							"B³¹d",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					if (em != null) {
						em.close();
					}
				}
			}			
		};

		worker.execute();
	}

	private void showDatabaseUnavailableMessage() {
		JOptionPane.showMessageDialog(mainFrame,
				"Wyst¹pi³ problem z baz¹ danych.\n" +
						"Zamknij wszystkie aplikacje, które mog¹ korzystaæ z bazy, i kliknij przycisk \"Odœwie¿\"",
				"B³¹d",
				JOptionPane.ERROR_MESSAGE);
	}

	private void closeApp() {
		Object[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(mainFrame,
				"Czy na pewno chcesz zamkn¹æ program?",
				"PotwierdŸ zamkniêcie",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			mainFrameService.shutdownDatabase();
			System.exit(0);
		}
	}

	private void removeSelectedBook() {
		Book book = bookPanelController.getSelectedBook();
		if (book == null) {
			JOptionPane.showMessageDialog(mainFrame,
					"Zaznacz ksi¹¿kê, któr¹ chcesz usun¹æ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(mainFrame,
				"Czy na pewno chcesz usun¹æ ksi¹¿kê \"" + book.getTitle() + "\" z bazy danych?",
				"PotwierdŸ",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			boolean success = false;
			EntityManager em = null;
			try {
				em = EntityManagerHandler.INSTANCE.getNewEntityManager();
				if (!mainFrameService.canBookBeRemoved(book, em)) {
					JOptionPane.showMessageDialog(mainFrame,
							"Nie mo¿na usun¹æ ksi¹¿ki \"" + book.getTitle() + "\" z bazy danych.\n" + 
									"Co najmniej jeden egzemplarz ksi¹¿ki jest obecnie wypo¿yczony",
							"B³¹d",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				success = mainFrameService.removeBook(book, em);
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
				updateBookTable();
			} else {
				JOptionPane.showMessageDialog(mainFrame,
						"Wyst¹pi³ b³¹d, nie uda³o siê usun¹æ ksi¹¿ki \"" + book.getTitle() + "\" z bazy danych.",
						"B³¹d",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void removeSelectedReader() {
		Reader reader = readerPanelController.getSelectedReader();

		if (reader == null) {
			JOptionPane.showMessageDialog(mainFrame,
					"Zaznacz czytelnika, którego chcesz usun¹æ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(mainFrame,
				"Czy na pewno chcesz usun¹æ czytelnika " + reader.getFirstName() + " " + reader.getLastName() + " z bazy danych?",
				"PotwierdŸ usuniêcie czytelnika",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			boolean success = false;
			EntityManager em = null;
			try {
				em = EntityManagerHandler.INSTANCE.getNewEntityManager();
				if (!mainFrameService.canReaderBeRemoved(reader, em)) {
					JOptionPane.showMessageDialog(mainFrame,
							"Nie mo¿na usun¹æ czytelnika " + reader.getFirstName() + " " + reader.getLastName() + " z bazy danych.\n" + 
									"Czytelnik ma niezwrócone ksi¹¿ki.",
							"B³¹d",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				success = mainFrameService.removeReader(reader, em);
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
				updateReaderTable();
			} else {
				JOptionPane.showMessageDialog(mainFrame,
						"B³¹d, nie uda³o siê usun¹æ czytelnika " + reader.getFirstName() + " " + reader.getLastName() + " z bazy danych.",
						"B³¹d",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void connectAndUpdate() {
		if (mainFrameService.restartConnection()) {
			updateBookTable();
			updateReaderTable();
			readerPanelController.updateBorrowedBooksTable();
		} else {
			showDatabaseUnavailableMessage();
		}
	}
	
	private void borrowBook() {
		Reader reader = readerPanelController.getSelectedReader();
		if (reader == null) {
			JOptionPane.showMessageDialog(mainFrame,
					"Zaznacz czytelnika, który chce wypo¿yczyæ ksi¹¿kê",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Book book = bookPanelController.getSelectedBook();
		if (book == null) {
			JOptionPane.showMessageDialog(mainFrame,
					"Zaznacz ksi¹¿kê, któr¹ chcesz wypo¿yczyæ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if (book.getCopies() < 1) {
			JOptionPane.showMessageDialog(mainFrame,
					"Ksi¹¿ka nie jest dostêpna do wypo¿yczenia",
					"Ksi¹¿ka niedostêpna",
					JOptionPane.INFORMATION_MESSAGE);			
			return;
		}

		LocalDate borrowDate = LocalDate.now();
		LocalDate returnDate = borrowDate.plusDays(30);

		BorrowTransaction transaction = new BorrowTransaction(reader, book, borrowDate, returnDate);
		boolean success = false;
		EntityManager em = null;
		try {
			em = EntityManagerHandler.INSTANCE.getNewEntityManager();
			success = mainFrameService.borrowBook(transaction, em);
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
			updateBookTable();
			readerPanelController.updateBorrowedBooksTable();
		} else {
			JOptionPane.showMessageDialog(mainFrame,
					"Wyst¹pi³ b³¹d, nie uda³o siê wypo¿yczyæ ksi¹¿ki \"" + book.getTitle() + "\".",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void editSelectedBook() {
		Book book = bookPanelController.getSelectedBook();
		if (book == null) {
			JOptionPane.showMessageDialog(mainFrame,
					"Zaznacz ksi¹¿kê, któr¹ chcesz edytowaæ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		editBookFormController.displayForm(book);
	}
	
	private void editSelectedReader() {
		Reader reader = readerPanelController.getSelectedReader();
		if (reader == null) {
			JOptionPane.showMessageDialog(mainFrame,
					"Zaznacz czytelnika, którego dane chcesz edytowaæ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		editReaderFormController.displayForm(reader);
	}
}
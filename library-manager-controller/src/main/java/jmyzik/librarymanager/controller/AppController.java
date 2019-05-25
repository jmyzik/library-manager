package jmyzik.librarymanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.callbacks.ReaderTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.enums.Actions;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
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
	
	private JButton refreshButton;
	private JButton borrowButton;
	
	public AppController(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		mainFrameService = new MainFrameService();
		appMenuBarController = new AppMenuBarController(mainFrame.getAppMenuBar());
		bookPanelController = new BookPanelController(mainFrame.getBookPanel());
		readerPanelController = new ReaderPanelController(mainFrame.getReaderPanel());
		addBookFormController = new AddBookFormController(mainFrame.getAddBookForm());
		addReaderFormController = new AddReaderFormController(mainFrame.getAddReaderForm());

		refreshButton = mainFrame.getRefreshButton();
		borrowButton = mainFrame.getBorrowButton();
		
		addListeners();
		setCallbacks();
	}
	
	private void addListeners() {
		appMenuBarController.addListeners(this);
		readerPanelController.addListeners(this);
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeApp();
			}
		});
		refreshButton.addActionListener(e -> connectAndUpdate());
		borrowButton.addActionListener(e -> borrowBook());
	}

	private void setCallbacks() {
		addBookFormController.setBookTableChangedCallback(this);
		addReaderFormController.setReaderTableChangedCallback(this);
		readerPanelController.setBookTableChangedCallback(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(Actions.ADD_BOOK.actionName())) {
			addBookFormController.displayForm();
		} else if (command.equals(Actions.ADD_READER.actionName())) {
			addReaderFormController.displayForm();
		} else if (command.equals(Actions.REMOVE_BOOK.actionName())) {
			removeSelectedBook();
		} else if (command.equals(Actions.REMOVE_READER.actionName())) {
			removeSelectedReader();
		} else if (command.equals(Actions.CLOSE_APP.actionName())) {
			closeApp();
		} else if (command.equals(Actions.BORROW_BOOK.actionName())) {
			borrowBook();
		} else if (command.equals(Actions.RETURN_BOOK.actionName())) {
			readerPanelController.returnSelectedBook();
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
			@Override
			protected List<Book> doInBackground() throws Exception {
				List<Book> bookList = new ArrayList<Book>();
				try {
					bookList = mainFrameService.getAllBooks();
				} catch (DatabaseUnavailableException e) {
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
							"Wyst�pi� b��d, nie uda�o si� wy�wietli� listy ksi��ek.",
							"B��d",
							JOptionPane.ERROR_MESSAGE);
				}
			}			
		};

		worker.execute();
	}

	private void updateReaderTable() {
		SwingWorker<List<Reader>, Void> worker = new SwingWorker<List<Reader>, Void>() {
			@Override
			protected List<Reader> doInBackground() throws Exception {
				List<Reader> readerList = new ArrayList<Reader>();
				try {
					readerList = mainFrameService.getAllReaders();
				} catch (DatabaseUnavailableException e) {
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
							"Wyst�pi� b��d, nie uda�o si� wy�wietli� listy czytelnik�w.",
							"B��d",
							JOptionPane.ERROR_MESSAGE);
				}
			}			
		};

		worker.execute();
	}

	private void showDatabaseUnavailableMessage() {
		JOptionPane.showMessageDialog(mainFrame,
				"Wyst�pi� problem z baz� danych.\n" +
						"Zamknij wszystkie aplikacje, kt�re mog� korzysta� z bazy, i kliknij przycisk \"Od�wie�\"",
				"B��d",
				JOptionPane.ERROR_MESSAGE);
	}

	private void closeApp() {
		Object[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(mainFrame,
				"Czy na pewno chcesz zamkn�� program?",
				"Potwierd� zamkni�cie",
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
					"Zaznacz ksi��k�, kt�r� chcesz usun��",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(mainFrame,
				"Czy na pewno chcesz usun�� ksi��k� \"" + book.getTitle() + "\" z bazy danych?",
				"Potwierd�",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			boolean success = false;
			try {
				success = mainFrameService.removeBook(book);
			} catch (DatabaseUnavailableException e) {
				showDatabaseUnavailableMessage();
			} 
			if (success) {
				updateBookTable();
			} else {
				JOptionPane.showMessageDialog(mainFrame,
						"Wyst�pi� b��d, nie uda�o si� usun�� ksi��ki \"" + book.getTitle() + "\" z bazy danych.",
						"B��d",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void removeSelectedReader() {
		Reader reader = readerPanelController.getSelectedReader();

		if (reader == null) {
			JOptionPane.showMessageDialog(mainFrame,
					"Zaznacz czytelnika, kt�rego chcesz usun��",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(mainFrame,
				"Czy na pewno chcesz usun�� czytelnika " + reader.getFirstName() + " " + reader.getLastName() + " z bazy danych?",
				"Potwierd� usuni�cie czytelnika",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			boolean success = false;
			try {
				success = mainFrameService.removeReader(reader);
			} catch (DatabaseUnavailableException e) {
				showDatabaseUnavailableMessage();
			} 
			if (success) {
				updateReaderTable();
			} else {
				JOptionPane.showMessageDialog(mainFrame,
						"B��d, nie uda�o si� usun�� czytelnika " + reader.getFirstName() + " " + reader.getLastName() + " z bazy danych.",
						"B��d",
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
					"Zaznacz czytelnika, kt�ry chce wypo�yczy� ksi��k�",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Book book = bookPanelController.getSelectedBook();
		if (book == null) {
			JOptionPane.showMessageDialog(mainFrame,
					"Zaznacz ksi��k�, kt�r� chcesz wypo�yczy�",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if (book.getCopies() < 1) {
			JOptionPane.showMessageDialog(mainFrame,
					"Ksi��ka nie jest dost�pna do wypo�yczenia",
					"Ksi��ka niedost�pna",
					JOptionPane.INFORMATION_MESSAGE);			
			return;
		}

		LocalDate borrowDate = LocalDate.now();
		LocalDate returnDate = borrowDate.plusDays(30);

		BorrowTransaction transaction = new BorrowTransaction(reader, book, borrowDate, returnDate);
		boolean success = false;
		try {
			success = mainFrameService.borrowBook(transaction);
		} catch (DatabaseUnavailableException e) {
			showDatabaseUnavailableMessage();
		}
		if (success) {
			updateBookTable();
			readerPanelController.updateBorrowedBooksTable();
		} else {
			JOptionPane.showMessageDialog(mainFrame,
					"Wyst�pi� b��d, nie uda�o si� wypo�yczy� ksi��ki \"" + book.getTitle() + "\".",
					"B��d",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
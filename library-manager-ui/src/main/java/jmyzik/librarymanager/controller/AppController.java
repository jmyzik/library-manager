package jmyzik.librarymanager.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.callbacks.ReaderTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.enums.Actions;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.MainFrameService;
import jmyzik.librarymanager.ui.AppMenuBar;
import jmyzik.librarymanager.ui.BookPanel;
import jmyzik.librarymanager.ui.MainFrame;
import jmyzik.librarymanager.ui.ReaderPanel;

public class AppController implements ActionListener, BookTableChangedCallback, ReaderTableChangedCallback {
	
	private MainFrame mainFrame;
	
	private MainFrameService mainFrameService;
	
	private AppMenuBarController appMenuBarController;
	private BookPanelController bookPanelController;
	private ReaderPanelController readerPanelController;
	private AddBookFormController addBookFormController;
	private AddReaderFormController addReaderFormController;
	
	private AppMenuBar appMenuBar;
	private BookPanel bookPanel;
	private ReaderPanel readerPanel;
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

		appMenuBar = mainFrame.getAppMenuBar();
		bookPanel = mainFrame.getBookPanel();
		readerPanel = mainFrame.getReaderPanel();
		refreshButton = mainFrame.getRefreshButton();
		borrowButton = mainFrame.getBorrowButton();
		
		addListeners();
		setCallbacks();
	}
	
	private void addListeners() {
		appMenuBarController.addListeners(this);
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
		String menuItem = e.getActionCommand();
		if (menuItem.equals(Actions.ADD_BOOK.actionName())) {
			addBookFormController.displayForm();
		} else if (menuItem.equals(Actions.ADD_READER.actionName())) {
			addReaderFormController.displayForm();
		} else if (menuItem.equals(Actions.REMOVE_BOOK.actionName())) {
			removeSelectedBook();
		} else if (menuItem.equals(Actions.REMOVE_READER.actionName())) {
			removeSelectedReader();
		} else if (menuItem.equals(Actions.CLOSE_APP.actionName())) {
			closeApp();
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
		List<Book> bookList = new ArrayList<Book>();
		try {
			bookList = mainFrameService.getAllBooks();
		} catch (DatabaseUnavailableException e) {
			showDatabaseUnavailableMessage();		} 
		bookPanelController.displayBooks(bookList);
	}

	private void updateReaderTable() {
		List<Reader> readerList = new ArrayList<Reader>();
		try {
			readerList = mainFrameService.getAllReaders();
		} catch (DatabaseUnavailableException e) {
			showDatabaseUnavailableMessage();		} 
		readerPanelController.displayReaders(readerList);
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
			try {
				success = mainFrameService.removeBook(book);
			} catch (DatabaseUnavailableException e) {
				showDatabaseUnavailableMessage();			} 
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
				"PotwierdŸ usuniêcie czytelnika", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
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
			JOptionPane.showMessageDialog(mainFrame,
					"Nie uda³o siê nawi¹zaæ po³¹czenia z baz¹ danych.\n" +
					"Zamknij wszystkie aplikacje, które mog¹ korzystaæ z bazy, i kliknij przycisk \"Odœwie¿\"",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
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
					"Wyst¹pi³ b³¹d, nie uda³o siê wypo¿yczyæ ksi¹¿ki \"" + book.getTitle() + "\".",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
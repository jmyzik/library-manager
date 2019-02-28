package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.callbacks.ReaderTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.BorrowTransaction;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.MainFrameService;

public class MainFrame extends JFrame implements BookTableChangedCallback, ReaderTableChangedCallback {

	private JPanel buttonPanel;
	private JButton refreshButton;
	private JButton borrowButton;
	private JTabbedPane tabbedPane;
	private BookTablePanel bookTablePanel;
	private ReaderTablePanel readerTablePanel;

	private AddBookForm addBookForm;
	private AddReaderForm addReaderForm;
	private MainFrameService mainFrameService;

	public MainFrame() {
		super("Library Manager");
		initializeVariables();
		constructAppWindow();
		constructLayout();
		addListeners();
		setCallbacks();
		connectAndUpdate();
	}

	private void initializeVariables() {
		buttonPanel = new JPanel();
		refreshButton = new JButton("Odœwie¿");
		borrowButton = new JButton("Wypo¿ycz");
		tabbedPane = new JTabbedPane();
		bookTablePanel = new BookTablePanel();
		readerTablePanel = new ReaderTablePanel();
		addBookForm = new AddBookForm(this);
		addReaderForm = new AddReaderForm(this);
		mainFrameService = new MainFrameService();
	}

	private void constructAppWindow() {
		setSize(600, 400);
		setMinimumSize(new Dimension(400, 200));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void constructLayout() {
		setJMenuBar(createMenuBar());

		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(refreshButton);
		buttonPanel.add(borrowButton);
		
		tabbedPane.addTab("Ksi¹¿ki", bookTablePanel);
		tabbedPane.addTab("Czytelnicy", readerTablePanel);

		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.PAGE_START);
		add(tabbedPane, BorderLayout.CENTER);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menuFile = new JMenu("Plik");
		JMenuItem menuItemAddBook = new JMenuItem("Dodaj ksi¹¿kê...");
		menuItemAddBook.addActionListener(e -> addBookForm.display());
		menuFile.add(menuItemAddBook);
		JMenuItem menuItemAddReader = new JMenuItem("Dodaj czytelnika...");
		menuItemAddReader.addActionListener(e -> addReaderForm.display());
		menuFile.add(menuItemAddReader);
		JMenuItem menuItemRemoveBook = new JMenuItem("Usuñ ksi¹¿kê");
		menuItemRemoveBook.addActionListener(e -> removeSelectedBook());
		menuFile.add(menuItemRemoveBook);
		JMenuItem menuItemRemoveReader = new JMenuItem("Usuñ czytelnika");
		menuItemRemoveReader.addActionListener(e -> removeSelectedReader());
		menuFile.add(menuItemRemoveReader);
		JMenuItem menuItemExit = new JMenuItem("WyjdŸ");
		menuItemExit.addActionListener(e -> closeApp());
		menuFile.add(menuItemExit);

		menuBar.add(menuFile);

		return menuBar;
	}

	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeApp();
			}
		});
		refreshButton.addActionListener(l -> connectAndUpdate());
		borrowButton.addActionListener(l -> borrowBook());
	}

	private void setCallbacks() {
		addBookForm.setBookTableChangedCallback(this);
		addReaderForm.setReaderTableChangedCallback(this);
	}

	private void updateBookTable() {
		List<Book> bookList = new ArrayList<Book>();
		try {
			bookList = mainFrameService.getAllBooks();
		} catch (DatabaseUnavailableException e) {
			showDatabaseUnavailableMessage();		} 
		bookTablePanel.displayBooks(bookList);
	}

	private void updateReaderTable() {
		List<Reader> readerList = new ArrayList<Reader>();
		try {
			readerList = mainFrameService.getAllReaders();
		} catch (DatabaseUnavailableException e) {
			showDatabaseUnavailableMessage();		} 
		readerTablePanel.displayReaders(readerList);
	}

	@Override
	public void bookTableChanged() {
		updateBookTable();
	}

	@Override
	public void readerTableChanged() {
		updateReaderTable();
	}

	private void closeApp() {
		Object[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(this,
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
		Book book = bookTablePanel.getSelectedBook();
		if (book == null) {
			JOptionPane.showMessageDialog(this,
					"Zaznacz ksi¹¿kê, któr¹ chcesz usun¹æ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(this,
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
				bookTableChanged();
			} else {
				JOptionPane.showMessageDialog(this,
						"Wyst¹pi³ b³¹d, nie uda³o siê usun¹æ ksi¹¿ki \"" + book.getTitle() + "\" z bazy danych.",
						"B³¹d",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void removeSelectedReader() {
		Reader reader = readerTablePanel.getSelectedReader();

		if (reader == null) {
			JOptionPane.showMessageDialog(this,
					"Zaznacz czytelnika, którego chcesz usun¹æ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(this,
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
				readerTableChanged();
			} else {
				JOptionPane.showMessageDialog(this,
						"B³¹d, nie uda³o siê usun¹æ czytelnika " + reader.getFirstName() + " " + reader.getLastName() + " z bazy danych.",
						"B³¹d",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void connectAndUpdate() {
		if (mainFrameService.restartConnection()) {
			updateBookTable();
			updateReaderTable();
		} else {
			JOptionPane.showMessageDialog(this,
					"Nie uda³o siê nawi¹zaæ po³¹czenia z baz¹ danych.\n" +
					"Zamknij wszystkie aplikacje, które mog¹ korzystaæ z bazy, i kliknij przycisk \"Odœwie¿\"",
					"B³¹d",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void borrowBook() {
		Reader reader = readerTablePanel.getSelectedReader();
		if (reader == null) {
			JOptionPane.showMessageDialog(this,
					"Zaznacz czytelnika, który chce wypo¿yczyæ ksi¹¿kê",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Book book = bookTablePanel.getSelectedBook();
		if (book == null) {
			JOptionPane.showMessageDialog(this,
					"Zaznacz ksi¹¿kê, któr¹ chcesz wypo¿yczyæ",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		LocalDate borrowDate = LocalDate.now();
		LocalDate returnDate = borrowDate.plusDays(30);

		BorrowTransaction transaction = new BorrowTransaction(reader, book, borrowDate, returnDate);
		try {
			mainFrameService.borrowBook(transaction);
		} catch (DatabaseUnavailableException e) {
			showDatabaseUnavailableMessage();
		}
	}
	
	private void showDatabaseUnavailableMessage() {
		JOptionPane.showMessageDialog(this,
				"Wyst¹pi³ problem z baz¹ danych.\n" +
						"Zamknij wszystkie aplikacje, które mog¹ korzystaæ z bazy, i kliknij przycisk \"Odœwie¿\"",
				"B³¹d",
				JOptionPane.ERROR_MESSAGE);			
	}
}
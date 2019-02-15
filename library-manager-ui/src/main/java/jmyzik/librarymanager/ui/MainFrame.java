package jmyzik.librarymanager.ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.callbacks.ReaderTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.service.MainFrameService;

public class MainFrame extends JFrame implements BookTableChangedCallback, ReaderTableChangedCallback {

	private JTabbedPane tabbedPane;
	private BookTablePanel bookTablePanel;
	private ReaderTablePanel readerTablePanel;

	private AddBookForm addBookForm;
	private AddReaderForm addReaderForm;
	private MainFrameService mainFrameService;

	public MainFrame() {
		super("Bibliotekarz");
		initializeVariables();
		constructAppWindow();
		constructLayout();
		addListeners();
		setCallbacks();
	}

	private void initializeVariables() {
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
		
		tabbedPane.addTab("Ksi��ki", bookTablePanel);
		updateBookTable();
		tabbedPane.addTab("Czytelnicy", readerTablePanel);
		updateReaderTable();
		add(tabbedPane);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menuFile = new JMenu("Plik");
		JMenuItem menuItemAddBook = new JMenuItem("Dodaj ksi��k�...");
		menuItemAddBook.addActionListener(e -> addBookForm.display());
		menuFile.add(menuItemAddBook);
		JMenuItem menuItemAddReader = new JMenuItem("Dodaj czytelnika...");
		menuItemAddReader.addActionListener(e -> addReaderForm.display());
		menuFile.add(menuItemAddReader);
		JMenuItem menuItemRemoveBook = new JMenuItem("Usu� ksi��k�");
		menuItemRemoveBook.addActionListener(e -> removeSelectedBook());
		menuFile.add(menuItemRemoveBook);
		JMenuItem menuItemRemoveReader = new JMenuItem("Usu� czytelnika");
		menuItemRemoveReader.addActionListener(e -> removeSelectedReader());
		menuFile.add(menuItemRemoveReader);
		JMenuItem menuItemExit = new JMenuItem("Wyjd�");
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
	}

	private void setCallbacks() {
		addBookForm.setBookTableChangedCallback(this);
		addReaderForm.setReaderTableChangedCallback(this);
	}

	private void updateBookTable() {
		List<Book> bookList = mainFrameService.getAllBooks();
		bookTablePanel.displayBooks(bookList);
	}

	private void updateReaderTable() {
		List<Reader> readerList = mainFrameService.getAllReaders();
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
		int result = JOptionPane.showOptionDialog(
				this,
				"Czy na pewno chcesz zamkn�� program?",
				"Potwierd� zamkni�cie",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
	
	private void removeSelectedBook() {
		Book book = bookTablePanel.getSelectedBook();
		if (book == null) {
			JOptionPane.showMessageDialog(this,
					"Zaznacz ksi��k�, kt�r� chcesz usun��",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		Object[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(
				this,
				"Czy na pewno chcesz usun�� ksi��k� \"" + book.getTitle() + "\" z bazy danych?",
				"Potwierd�",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			boolean success = mainFrameService.removeBook(book);
			bookTableChanged();
			if (!success) {
				JOptionPane.showMessageDialog(this,
						"Wyst�pi� b��d, nie uda�o si� usun�� ksi��ki \"" + book.getTitle() + "\" z bazy danych.",
						"B��d",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void removeSelectedReader() {
		Reader reader = readerTablePanel.getSelectedReader();
		
		if (reader == null) {
			JOptionPane.showMessageDialog(this,
					"Zaznacz czytelnika, kt�rego chcesz usun��",
					"Brak zaznaczenia",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		Object[] options = { "Tak", "Nie" };
		int result = JOptionPane.showOptionDialog(
				this,
				"Czy na pewno chcesz usun�� czytelnika " + reader.getFirstName() + " " + reader.getLastName() + " z bazy danych?",
				"Potwierd� usuni�cie czytelnika",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[0]);
		if (result == JOptionPane.YES_OPTION) {
			boolean success = mainFrameService.removeReader(reader);
			readerTableChanged();
			if (!success) {
				JOptionPane.showMessageDialog(this,
						"B��d, nie uda�o si� usun�� czytelnika \" + reader.getFirstName() + \" \" + reader.getLastName() + \" z bazy danych.",
						"B��d",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import jmyzik.librarymanager.enums.Actions;

public class MainFrame extends JFrame {

	private JPanel buttonPanel;
	private JButton refreshButton;
	private JButton borrowButton;
	private JTabbedPane tabbedPane;
	private BookPanel bookPanel;
	private ReaderPanel readerPanel;

	private AddBookForm addBookForm;
	private AddReaderForm addReaderForm;

	public MainFrame() {
		super("Library Manager");
		initializeVariables();
		constructAppWindow();
		constructLayout();
	}

	private void initializeVariables() {
		buttonPanel = new JPanel();
		refreshButton = new JButton("Odœwie¿");
		borrowButton = new JButton("Wypo¿ycz");
		tabbedPane = new JTabbedPane();
		bookPanel = new BookPanel();
		readerPanel = new ReaderPanel();
		addBookForm = new AddBookForm(this);
		addReaderForm = new AddReaderForm(this);
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
		
		tabbedPane.addTab("Ksi¹¿ki", bookPanel);
		tabbedPane.addTab("Czytelnicy", readerPanel);

		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.PAGE_START);
		add(tabbedPane, BorderLayout.CENTER);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu menuFile = new JMenu("Plik");
		JMenuItem menuItemAddBook = new JMenuItem("Dodaj ksi¹¿kê...");
		menuItemAddBook.setActionCommand(Actions.ADD_BOOK.actionName());
		menuFile.add(menuItemAddBook);
		JMenuItem menuItemAddReader = new JMenuItem("Dodaj czytelnika...");
		menuItemAddReader.setActionCommand(Actions.ADD_READER.actionName());
		menuFile.add(menuItemAddReader);
		JMenuItem menuItemRemoveBook = new JMenuItem("Usuñ ksi¹¿kê");
		menuItemRemoveBook.setActionCommand(Actions.REMOVE_BOOK.actionName());
		menuFile.add(menuItemRemoveBook);
		JMenuItem menuItemRemoveReader = new JMenuItem("Usuñ czytelnika");
		menuItemRemoveReader.setActionCommand(Actions.REMOVE_READER.actionName());
//		menuItemRemoveReader.addActionListener(e -> removeSelectedReader());
		menuFile.add(menuItemRemoveReader);
		JMenuItem menuItemExit = new JMenuItem("WyjdŸ");
//		menuItemExit.addActionListener(e -> closeApp());
		menuFile.add(menuItemExit);

		menuBar.add(menuFile);

		return menuBar;
	}

	public BookPanel getBookPanel() {
		return bookPanel;
	}

	public ReaderPanel getReaderPanel() {
		return readerPanel;
	}

	public AddBookForm getAddBookForm() {
		return addBookForm;
	}

	public AddReaderForm getAddReaderForm() {
		return addReaderForm;
	}

	public JButton getRefreshButton() {
		return refreshButton;
	}

	public JButton getBorrowButton() {
		return borrowButton;
	}
}
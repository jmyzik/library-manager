package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

	private AppMenuBar appMenuBar;
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
		appMenuBar = new AppMenuBar();
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
		setJMenuBar(appMenuBar);

		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(refreshButton);
		buttonPanel.add(borrowButton);
		
		tabbedPane.addTab("Ksi¹¿ki", bookPanel);
		tabbedPane.addTab("Czytelnicy", readerPanel);

		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.PAGE_START);
		add(tabbedPane, BorderLayout.CENTER);
	}

	public AppMenuBar getAppMenuBar() {
		return appMenuBar;
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
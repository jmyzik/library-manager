package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MainFrame extends JFrame {

	private AppMenuBar appMenuBar;
	private JPanel buttonPanel;
	private JButton refreshButton;
	private JTabbedPane tabbedPane;
	private BookPanel bookPanel;
	private ReaderPanel readerPanel;

	private AddBookForm addBookForm;
	private EditBookForm editBookForm;
	private AddReaderForm addReaderForm;
	private EditReaderForm editReaderForm;

	public MainFrame() {
		super("Library Manager");
		initializeVariables();
		setupAppWindow();
		constructLayout();
	}

	private void initializeVariables() {
		appMenuBar = new AppMenuBar();
		buttonPanel = new JPanel();
		refreshButton = new JButton("Od�wie�");
		tabbedPane = new JTabbedPane();
		bookPanel = new BookPanel();
		readerPanel = new ReaderPanel();
		
		addBookForm = new AddBookForm(this);
		editBookForm = new EditBookForm(this);
		addReaderForm = new AddReaderForm(this);
		editReaderForm = new EditReaderForm(this);
	}

	private void setupAppWindow() {
		setSize(600, 400);
		setMinimumSize(new Dimension(400, 200));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void constructLayout() {
		setJMenuBar(appMenuBar);

		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(refreshButton);
		
		tabbedPane.addTab("Ksi��ki", bookPanel);
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

	public EditBookForm getEditBookForm() {
		return editBookForm;
	}
	
	public AddReaderForm getAddReaderForm() {
		return addReaderForm;
	}

	public EditReaderForm getEditReaderForm() {
		return editReaderForm;
	}
	
	public JButton getRefreshButton() {
		return refreshButton;
	}
}
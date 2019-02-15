package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import jmyzik.librarymanager.callbacks.BookTableChangedCallback;
import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.service.AddBookFormService;

public class AddBookForm extends JDialog implements ActionListener {

	private JFrame parentFrame;
	private JButton cancelButton;
	private JButton saveButton;
	private JLabel titleLabel;
	private JLabel authorLabel;
	private JLabel publisherLabel;
	private JLabel publicationYearLabel;
	private JTextField titleField;
	private JTextField authorField;
	private JTextField publisherField;
	private JTextField publicationYearField;
	private BookTableChangedCallback bookTableChangedCallback;
	private AddBookFormService addBookFormService;

	public AddBookForm(JFrame parentFrame) {
		super(parentFrame, "Dodaj ksi¹¿kê", true);

		this.parentFrame = parentFrame;
		initializeVariables();
		constructLayout();
		setWindow();
	}

	private void initializeVariables() {
		cancelButton = new JButton("Anuluj");
		saveButton = new JButton("Zapisz");
		cancelButton.addActionListener(this);
		saveButton.addActionListener(this);
		titleLabel = new JLabel("Tytu³");
		titleField = new JTextField(15);
		authorLabel = new JLabel("Autor");
		authorField = new JTextField(15);
		publisherLabel = new JLabel("Wydawnictwo");
		publisherField = new JTextField(15);
		publicationYearLabel = new JLabel("Rok wydania");
		publicationYearField = new JTextField(15);
		addBookFormService = new AddBookFormService();
	}

	private void constructLayout() {

		JPanel bookInfoPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		JLabel[] labels = new JLabel[] {titleLabel, authorLabel, publisherLabel, publicationYearLabel};
		JTextField[] textFields = new JTextField[] {titleField, authorField, publisherField, publicationYearField};
		
		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Dodaj now¹ ksi¹¿kê");
		bookInfoPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));

		bookInfoPanel.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
		Insets rightPadding = new Insets(0, 0, 0, 15);
		Insets noPadding = new Insets(0, 0, 0, 0);

		for (int i = 0; i < labels.length; i++) {
			gc.gridy = i;

			gc.gridx = 0;
			gc.anchor = GridBagConstraints.EAST;
			gc.insets = rightPadding;
			bookInfoPanel.add(labels[i], gc);

			gc.gridx = 1;
			gc.anchor = GridBagConstraints.WEST;
			gc.insets = noPadding;
			bookInfoPanel.add(textFields[i], gc);			
		}

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonsPanel.add(saveButton);
		buttonsPanel.add(cancelButton);

		setLayout(new BorderLayout());
		add(bookInfoPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
	}

	private void setWindow() {
		setSize(350, 250);
		setResizable(false);
		setLocationRelativeTo(parentFrame);
	}
	
	public void setBookTableChangedCallback(BookTableChangedCallback bookTableChangedCallback) {
		this.bookTableChangedCallback = bookTableChangedCallback;
	}	
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == cancelButton) {
			setVisible(false);
		} else if (event.getSource() == saveButton) {
			String title = titleField.getText();
			String author = authorField.getText();
			String publisher = publisherField.getText();
			int publicationYear;
			try {
				publicationYear = Integer.parseInt(publicationYearField.getText());
			} catch (NumberFormatException e) {
				publicationYear = -1;
			}
			
			if(!validateData(title, author, publisher, publicationYear)) {
				JOptionPane.showMessageDialog(this, "WprowadŸ poprawne dane", "B³êdne dane", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Book newBook = new Book(99, title, author, publisher, publicationYear);	// TODO: set automatic ID!

			addBookFormService.addBook(newBook);
			bookTableChangedCallback.bookTableChanged();
			setVisible(false);
		}
	}
	
	private boolean validateData(String title, String author, String publisher, int publicationYear) {
		return !title.equals("") &&
				!author.equals("") &&
				!publisher.equals("") &&
				publicationYear != -1;
	}
	
	public void display() {
		setLocationRelativeTo(parentFrame);
		titleField.setText("");
		authorField.setText("");
		publisherField.setText("");
		publicationYearField.setText("");
		setVisible(true);
		titleField.requestFocusInWindow();
	}
}
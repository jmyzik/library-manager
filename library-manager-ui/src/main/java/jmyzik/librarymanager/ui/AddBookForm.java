package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class AddBookForm extends JDialog {

	private JFrame parentFrame;
	
	private JButton cancelButton;
	private JButton saveButton;
	
	private JLabel titleLabel;
	private JLabel authorLabel;
	private JLabel publisherLabel;
	private JLabel publicationYearLabel;
	private JLabel copiesLabel;
	
	private JTextField titleField;
	private JTextField authorField;
	private JTextField publisherField;
	private JTextField publicationYearField;
	private JTextField copiesField;
		
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
	
		titleLabel = new JLabel("Tytu³");
		titleField = new JTextField(15);
		authorLabel = new JLabel("Autor");
		authorField = new JTextField(15);
		publisherLabel = new JLabel("Wydawnictwo");
		publisherField = new JTextField(15);
		publicationYearLabel = new JLabel("Rok wydania");
		publicationYearField = new JTextField(15);
		copiesLabel = new JLabel("Liczba egzemplarzy");
		copiesField = new JTextField(15);
	}
	
	private void constructLayout() {

		JPanel bookInfoPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		JLabel[] labels = new JLabel[] {titleLabel, authorLabel, publisherLabel, publicationYearLabel, copiesLabel};
		JTextField[] textFields = new JTextField[] {titleField, authorField, publisherField, publicationYearField, copiesField};
		
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
		add(buttonsPanel, BorderLayout.PAGE_END);
	}

	private void setWindow() {
		setSize(350, 280);
		setResizable(false);
		setLocationRelativeTo(parentFrame);
	}
	
	public void display() {
		setLocationRelativeTo(parentFrame);
		titleField.setText("");
		authorField.setText("");
		publisherField.setText("");
		publicationYearField.setText("");
		copiesField.setText("");
		setVisible(true);
		titleField.requestFocusInWindow();
	}
	
	public JButton getCancelButton() {
		return cancelButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JTextField getTitleField() {
		return titleField;
	}

	public JTextField getAuthorField() {
		return authorField;
	}

	public JTextField getPublisherField() {
		return publisherField;
	}

	public JTextField getPublicationYearField() {
		return publicationYearField;
	}

	public JTextField getCopiesField() {
		return copiesField;
	}	
}
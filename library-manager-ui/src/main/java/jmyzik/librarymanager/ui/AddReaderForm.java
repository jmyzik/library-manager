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

import jmyzik.librarymanager.callbacks.ReaderTableChangedCallback;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.AddReaderFormService;

public class AddReaderForm extends JDialog implements ActionListener {

	private JFrame parentFrame;
	private JButton cancelButton;
	private JButton saveButton;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel addressLabel;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField addressField;
	private ReaderTableChangedCallback readerTableChangedCallback;
	private AddReaderFormService addReaderFormService;

	public AddReaderForm(JFrame parentFrame) {
		super(parentFrame, "Dodaj czytelnika", true);

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
		firstNameLabel = new JLabel("Imiê");
		firstNameField = new JTextField(15);
		lastNameLabel = new JLabel("Nazwisko");
		lastNameField = new JTextField(15);
		addressLabel = new JLabel("Adres");
		addressField = new JTextField(15);
		addReaderFormService = new AddReaderFormService();
	}

	private void constructLayout() {

		JPanel bookInfoPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();
		
		JLabel[] labels = new JLabel[] {firstNameLabel, lastNameLabel, addressLabel};
		JTextField[] textFields = new JTextField[] {firstNameField, lastNameField, addressField};
		
		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
		Border titleBorder = BorderFactory.createTitledBorder("Dodaj nowego czytelnika");
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
		setSize(350, 220);
		setResizable(false);
		setLocationRelativeTo(parentFrame);
	}
	
	public void setReaderTableChangedCallback(ReaderTableChangedCallback readerTableChangedCallback) {
		this.readerTableChangedCallback = readerTableChangedCallback;
	}	
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == cancelButton) {
			setVisible(false);
		} else if (event.getSource() == saveButton) {
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String address = addressField.getText();
			
			if(!validateData(firstName, lastName, address)) {
				JOptionPane.showMessageDialog(this, "WprowadŸ poprawne dane", "B³êdne dane", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Reader newReader = new Reader(firstName, lastName, address);
			
			try {
				addReaderFormService.addReader(newReader);
				readerTableChangedCallback.readerTableChanged();
			} catch (DatabaseUnavailableException e) {
				JOptionPane.showMessageDialog(this,
						"Wyst¹pi³ problem z baz¹ danych, zmiany nie zosta³y wprowadzone",
						"B³¹d",
						JOptionPane.ERROR_MESSAGE);
			}
			setVisible(false);
		}
	}
	
	private boolean validateData(String name, String author, String publisher) {
		return !name.equals("") &&
				!author.equals("") &&
				!publisher.equals("");
	}
	
	public void display() {
		setLocationRelativeTo(parentFrame);
		firstNameField.setText("");
		lastNameField.setText("");
		addressField.setText("");
		setVisible(true);
		firstNameField.requestFocusInWindow();
	}
}
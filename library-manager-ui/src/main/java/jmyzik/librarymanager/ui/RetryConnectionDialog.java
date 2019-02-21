package jmyzik.librarymanager.ui;

import javax.swing.JOptionPane;

public class RetryConnectionDialog extends JOptionPane {
	private static final String message = "B��d po��czenia z baz� danych. "
			+ "Zamknij inne programy, kt�re mog� korzysta� z bazy danych, i kliknij przycisk \"Spr�buj ponownie\"";
	private static final String[] options = new String[] { "Spr�buj ponownie", "Anuluj" };

	public RetryConnectionDialog() {
		super(message, ERROR_MESSAGE, DEFAULT_OPTION, null, options, options[0]);
	}
	
	public void retryConnection() {
		setVisible(true);
// TODO write implementation
	}
}

package jmyzik.librarymanager.ui;

import javax.swing.JOptionPane;

public class RetryConnectionDialog extends JOptionPane {
	private static final String message = "B³¹d po³¹czenia z baz¹ danych. "
			+ "Zamknij inne programy, które mog¹ korzystaæ z bazy danych, i kliknij przycisk \"Spróbuj ponownie\"";
	private static final String[] options = new String[] { "Spróbuj ponownie", "Anuluj" };

	public RetryConnectionDialog() {
		super(message, ERROR_MESSAGE, DEFAULT_OPTION, null, options, options[0]);
	}
	
	public void retryConnection() {
		setVisible(true);
// TODO write implementation
	}
}

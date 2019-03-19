package jmyzik.librarymanager.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import jmyzik.librarymanager.ui.MainFrame;

public class App {

	public static void main (String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException |
				InstantiationException |
				IllegalAccessException |
				UnsupportedLookAndFeelException e) {
		}
		
		SwingUtilities.invokeLater(() -> new MainFrame());
	}
}
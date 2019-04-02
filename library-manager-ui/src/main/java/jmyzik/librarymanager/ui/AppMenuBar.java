package jmyzik.librarymanager.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import jmyzik.librarymanager.enums.Actions;

public class AppMenuBar extends JMenuBar {
	
	private JMenu menuFile;
	private JMenuItem menuItemAddBook;
	private JMenuItem menuItemAddReader;
	private JMenuItem menuItemRemoveBook;
	private JMenuItem menuItemRemoveReader;
	private JMenuItem menuItemExit;
	
	public AppMenuBar() {
		initializeVariables();
		defineActions();
		constructMenu();
	}
	
	private void initializeVariables() {
		menuFile = new JMenu("Plik");
		menuItemAddBook = new JMenuItem("Dodaj ksi¹¿kê...");
		menuItemAddReader = new JMenuItem("Dodaj czytelnika...");
		menuItemRemoveBook = new JMenuItem("Usuñ ksi¹¿kê");
		menuItemRemoveReader = new JMenuItem("Usuñ czytelnika");
		menuItemExit = new JMenuItem("WyjdŸ");
	}
	
	private void defineActions() {
		menuItemAddBook.setActionCommand(Actions.ADD_BOOK.actionName());
		menuItemAddReader.setActionCommand(Actions.ADD_READER.actionName());
		menuItemRemoveBook.setActionCommand(Actions.REMOVE_BOOK.actionName());
		menuItemRemoveReader.setActionCommand(Actions.REMOVE_READER.actionName());
		menuItemExit.setActionCommand(Actions.CLOSE_APP.actionName());
	}

	private void constructMenu() {
		menuFile.add(menuItemAddBook);
		menuFile.add(menuItemAddReader);
		menuFile.add(menuItemRemoveBook);
		menuFile.add(menuItemRemoveReader);
		menuFile.add(menuItemExit);

		this.add(menuFile);
	}

	public JMenuItem getMenuItemAddBook() {
		return menuItemAddBook;
	}

	public JMenuItem getMenuItemAddReader() {
		return menuItemAddReader;
	}

	public JMenuItem getMenuItemRemoveBook() {
		return menuItemRemoveBook;
	}

	public JMenuItem getMenuItemRemoveReader() {
		return menuItemRemoveReader;
	}

	public JMenuItem getMenuItemExit() {
		return menuItemExit;
	}
}
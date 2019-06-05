package jmyzik.librarymanager.ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import jmyzik.librarymanager.enums.Actions;

public class AppMenuBar extends JMenuBar {
	
	private JMenu menuFile;
	private JMenuItem menuItemExit;

	private JMenu menuBook;
	private JMenuItem menuItemAddBook;
	private JMenuItem menuItemEditBook;
	private JMenuItem menuItemRemoveBook;

	private JMenu menuReader;
	private JMenuItem menuItemAddReader;
	private JMenuItem menuItemEditReader;
	private JMenuItem menuItemRemoveReader;
		
	public AppMenuBar() {
		initializeVariables();
		defineActions();
		constructMenu();
	}
	
	private void initializeVariables() {
		menuFile = new JMenu("Plik");
		menuItemExit = new JMenuItem("WyjdŸ");

		menuBook = new JMenu("Ksi¹¿ka");
		menuItemAddBook = new JMenuItem("Dodaj ksi¹¿kê...");
		menuItemEditBook = new JMenuItem("Edytuj ksi¹¿kê...");
		menuItemRemoveBook = new JMenuItem("Usuñ ksi¹¿kê");

		menuReader = new JMenu("Czytelnik");
		menuItemAddReader = new JMenuItem("Dodaj czytelnika...");
		menuItemEditReader = new JMenuItem("Edytuj czytelnika...");
		menuItemRemoveReader = new JMenuItem("Usuñ czytelnika");
	}
	
	private void defineActions() {
		menuItemExit.setActionCommand(Actions.CLOSE_APP.actionName());

		menuItemAddBook.setActionCommand(Actions.ADD_BOOK.actionName());
		menuItemEditBook.setActionCommand(Actions.EDIT_BOOK.actionName());
		menuItemRemoveBook.setActionCommand(Actions.REMOVE_BOOK.actionName());

		menuItemAddReader.setActionCommand(Actions.ADD_READER.actionName());
		menuItemEditReader.setActionCommand(Actions.EDIT_READER.actionName());
		menuItemRemoveReader.setActionCommand(Actions.REMOVE_READER.actionName());
	}

	private void constructMenu() {
		menuFile.add(menuItemExit);

		menuBook.add(menuItemAddBook);
		menuBook.add(menuItemEditBook);
		menuBook.add(menuItemRemoveBook);

		menuReader.add(menuItemAddReader);
		menuReader.add(menuItemEditReader);
		menuReader.add(menuItemRemoveReader);

		this.add(menuFile);
		this.add(menuBook);
		this.add(menuReader);
	}

	public JMenuItem getMenuItemExit() {
		return menuItemExit;
	}

	public JMenuItem getMenuItemAddBook() {
		return menuItemAddBook;
	}

	public JMenuItem getMenuItemEditBook() {
		return menuItemEditBook;
	}

	public JMenuItem getMenuItemRemoveBook() {
		return menuItemRemoveBook;
	}

	public JMenuItem getMenuItemAddReader() {
		return menuItemAddReader;
	}

	public JMenuItem getMenuItemEditReader() {
		return menuItemEditReader;
	}

	public JMenuItem getMenuItemRemoveReader() {
		return menuItemRemoveReader;
	}
}
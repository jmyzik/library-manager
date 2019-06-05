package jmyzik.librarymanager.controller;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import jmyzik.librarymanager.ui.AppMenuBar;

public class AppMenuBarController {
	
	private JMenuItem menuItemExit;

	private JMenuItem menuItemAddBook;
	private JMenuItem menuItemEditBook;
	private JMenuItem menuItemRemoveBook;
	
	private JMenuItem menuItemAddReader;
	private JMenuItem menuItemEditReader;
	private JMenuItem menuItemRemoveReader;

	public AppMenuBarController(AppMenuBar appMenuBar) {
		menuItemExit = appMenuBar.getMenuItemExit();

		menuItemAddBook = appMenuBar.getMenuItemAddBook();
		menuItemEditBook = appMenuBar.getMenuItemEditBook();
		menuItemRemoveBook = appMenuBar.getMenuItemRemoveBook();

		menuItemAddReader = appMenuBar.getMenuItemAddReader();
		menuItemEditReader = appMenuBar.getMenuItemEditReader();
		menuItemRemoveReader = appMenuBar.getMenuItemRemoveReader();
	}
	
	public void addListeners(ActionListener listener) {
		menuItemExit.addActionListener(listener);

		menuItemAddBook.addActionListener(listener);
		menuItemEditBook.addActionListener(listener);
		menuItemRemoveBook.addActionListener(listener);

		menuItemAddReader.addActionListener(listener);
		menuItemEditReader.addActionListener(listener);
		menuItemRemoveReader.addActionListener(listener);
	}
}

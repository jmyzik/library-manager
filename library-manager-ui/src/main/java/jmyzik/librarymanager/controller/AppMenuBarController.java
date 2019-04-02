package jmyzik.librarymanager.controller;

import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import jmyzik.librarymanager.ui.AppMenuBar;

public class AppMenuBarController {
	
	private AppMenuBar appMenuBar;
	
	private JMenuItem menuItemAddBook;
	private JMenuItem menuItemAddReader;
	private JMenuItem menuItemRemoveBook;
	private JMenuItem menuItemRemoveReader;
	private JMenuItem menuItemExit;

	public AppMenuBarController(AppMenuBar appMenuBar) {
		this.appMenuBar = appMenuBar;
		
		menuItemAddBook = appMenuBar.getMenuItemAddBook();
		menuItemAddReader = appMenuBar.getMenuItemAddReader();
		menuItemRemoveBook = appMenuBar.getMenuItemRemoveBook();
		menuItemRemoveReader = appMenuBar.getMenuItemRemoveReader();
		menuItemExit = appMenuBar.getMenuItemExit();
	}
	
	public void addListeners(ActionListener listener) {
		menuItemAddBook.addActionListener(listener);
		menuItemAddReader.addActionListener(listener);
		menuItemRemoveBook.addActionListener(listener);
		menuItemRemoveReader.addActionListener(listener);
		menuItemExit.addActionListener(listener);
	}
}

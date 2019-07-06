package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import jmyzik.librarymanager.enums.Actions;

public class ReaderPanel extends JPanel {

	private ReaderTableModel readerTableModel;
	private BorrowedBooksTableModel borrowedBooksTableModel;
	private JTable readerTable;
	private JPanel detailsPanel;
	private JLabel nameLabel;
	private JTable borrowedBooksTable;
	private JPanel buttonPanel;
	private JButton editButton;
	private JButton borrowButton;
	private JButton returnButton;

	public ReaderPanel() {
		initalizeVariables();
		defineActions();
		setUpReaderTable();
		setUpBorrowedBooksTable();
		constructLayout();
	}

	private void initalizeVariables() {
		readerTableModel = new ReaderTableModel();
		borrowedBooksTableModel = new BorrowedBooksTableModel();
		readerTable = new JTable();		
		detailsPanel = new JPanel();
		nameLabel = new JLabel("Imiê i nazwisko");
		borrowedBooksTable = new JTable();
		buttonPanel = new JPanel();
		editButton = new JButton("Edytuj dane");
		borrowButton = new JButton("Wypo¿ycz ksi¹¿kê");
		returnButton = new JButton("Zwróæ ksi¹¿kê");
	}
	
	private void defineActions() {
		editButton.setActionCommand(Actions.EDIT_READER.actionName());
		borrowButton.setActionCommand(Actions.BORROW_BOOK.actionName());
		returnButton.setActionCommand(Actions.RETURN_BOOK.actionName());
	}
	
	private void setUpReaderTable() {
		readerTable.setModel(readerTableModel);
		readerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		readerTable.setFillsViewportHeight(true);
		readerTable.setAutoCreateRowSorter(true);
		setReaderTableColumnWidths();
	}
	
	private void setUpBorrowedBooksTable() {
		borrowedBooksTable.setModel(borrowedBooksTableModel);
		borrowedBooksTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		borrowedBooksTable.setFillsViewportHeight(true);
		borrowedBooksTable.setAutoCreateRowSorter(true);
		setBorrowedBooksTableColumnWidths();
	}

	private void setReaderTableColumnWidths() {
		int[] widths = { 40, 100, 140, 320 };
		for (int i = 0; i < widths.length; i++) {
			readerTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);			
		}
	}
	
	private void setBorrowedBooksTableColumnWidths() {
		int[] widths = { 140, 100, 80, 80 };
		for (int i = 0; i < widths.length; i++) {
			borrowedBooksTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);			
		}
	}
	
	private void constructLayout() {
		setLayout(new GridLayout(2, 1, 0, 10));
		add(new JScrollPane(readerTable));
		
		detailsPanel.setLayout(new BorderLayout(10, 5));
		
		nameLabel.setFont(new Font("Arial", Font.BOLD,14));
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		detailsPanel.add(nameLabel, BorderLayout.PAGE_START);
		detailsPanel.add(new JScrollPane(borrowedBooksTable), BorderLayout.CENTER);
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 10));
		buttonPanel.add(editButton);
		buttonPanel.add(borrowButton);
		buttonPanel.add(returnButton);
		detailsPanel.add(buttonPanel, BorderLayout.LINE_END);
		add(detailsPanel);
	}
	
	public ReaderTableModel getReaderTableModel() {
		return readerTableModel;
	}

	public BorrowedBooksTableModel getBorrowedBooksTableModel() {
		return borrowedBooksTableModel;
	}

	public JTable getReaderTable() {
		return readerTable;
	}

	public JLabel getNameLabel() {
		return nameLabel;
	}

	public JTable getBorrowedBooksTable() {
		return borrowedBooksTable;
	}
	
	public JButton getEditButton() {
		return editButton;
	}

	public JButton getBorrowButton() {
		return borrowButton;
	}

	public JButton getReturnButton() {
		return returnButton;
	}
}
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

public class BookPanel extends JPanel {

	private BookTableModel bookTableModel;
	private BorrowersTableModel borrowersTableModel;
	private JTable bookTable;
	private JPanel detailsPanel;
	private JLabel titleLabel;
	private JTable borrowersTable;
	private JPanel buttonPanel;
	private JButton editButton;
	private JButton borrowButton;
	private JButton returnButton;
	private JButton prolongButton;

	public BookPanel() {
		initalizeVariables();
		defineActions();
		setUpBookTable();
		setUpBorrowersTable();
		constructLayout();
	}

	private void initalizeVariables() {
		bookTableModel = new BookTableModel();
		borrowersTableModel = new BorrowersTableModel();
		bookTable = new JTable();
		detailsPanel = new JPanel();
		titleLabel = new JLabel("Tytu³");
		borrowersTable = new JTable();
		buttonPanel = new JPanel();
		editButton = new JButton("Edytuj dane");
		borrowButton = new JButton("Wypo¿ycz ksi¹¿kê");
		returnButton = new JButton("Zwróæ ksi¹¿kê");
		prolongButton = new JButton("Prolonguj");
	}
	
	private void defineActions() {
		editButton.setActionCommand(Actions.EDIT_BOOK.actionName());
		borrowButton.setActionCommand(Actions.BORROW_BOOK.actionName());
	}

	private void setUpBookTable() {
		bookTable.setModel(bookTableModel);
		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		bookTable.setFillsViewportHeight(true);
		bookTable.setAutoCreateRowSorter(true);
		setBookTableColumnWidths();
	}
	
	private void setUpBorrowersTable() {
		borrowersTable.setModel(borrowersTableModel);
		borrowersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		borrowersTable.setFillsViewportHeight(true);
		borrowersTable.setAutoCreateRowSorter(true);
		setBorrowersTableColumnWidths();
	}

	private void setBookTableColumnWidths() {
		int[] widths = { 40, 200, 160, 140, 80, 80 };
		for (int i = 0; i < widths.length; i++) {
			bookTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);			
		}
	}
	
	private void setBorrowersTableColumnWidths() {
		int[] widths = { 140, 130, 130 };
		for (int i = 0; i < widths.length; i++) {
			borrowersTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);			
		}
	}
	
	private void constructLayout() {
		setLayout(new GridLayout(2, 1, 0, 10));
		add(new JScrollPane(bookTable));

		detailsPanel.setLayout(new BorderLayout(10, 5));

		titleLabel.setFont(new Font("Arial", Font.BOLD,14));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		detailsPanel.add(titleLabel, BorderLayout.PAGE_START);
		detailsPanel.add(new JScrollPane(borrowersTable), BorderLayout.CENTER);
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 10));
		buttonPanel.add(editButton);
		buttonPanel.add(borrowButton);
		buttonPanel.add(returnButton);
		buttonPanel.add(prolongButton);
		detailsPanel.add(buttonPanel, BorderLayout.LINE_END);
		add(detailsPanel);
	}
	
	public BookTableModel getBookTableModel() {
		return bookTableModel;
	}

	public BorrowersTableModel getBorrowersTableModel() {
		return borrowersTableModel;
	}

	public JTable getBookTable() {
		return bookTable;
	}
	
	public JLabel getTitleLabel() {
		return titleLabel;
	}

	public JTable getBorrowersTable() {
		return borrowersTable;
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
	
	public JButton getProlongButton() {
		return prolongButton;
	}
}
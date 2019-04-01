package jmyzik.librarymanager.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class BookPanel extends JPanel {

	private BookTableModel tableModel;
	private JTable table;

	public BookPanel() {
		initalizeVariables();
		setUpTable();
		constructLayout();
	}

	private void initalizeVariables() {
		tableModel = new BookTableModel();
		table = new JTable();
	}

	private void setUpTable() {
		table.setModel(tableModel);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		setColumnWidths();
	}
	
	private void setColumnWidths() {
		int[] widths = { 40, 200, 160, 140, 80, 80 };
		for (int i = 0; i < widths.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);			
		}
	}
	
	private void constructLayout() {
		setLayout(new BorderLayout());
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	public BookTableModel getTableModel() {
		return tableModel;
	}

	public JTable getTable() {
		return table;
	}
}
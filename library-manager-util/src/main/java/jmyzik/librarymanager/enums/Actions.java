package jmyzik.librarymanager.enums;

public enum Actions {
	ADD_BOOK, ADD_READER, REMOVE_BOOK, REMOVE_READER, CLOSE_APP, BORROW_BOOK, RETURN_BOOK;
	
	public String actionName() {
		return toString();
	}
}

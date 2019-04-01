package jmyzik.librarymanager.enums;

public enum Actions {
	ADD_BOOK, ADD_READER, REMOVE_BOOK, REMOVE_READER, CLOSE_APP;
	
	public String actionName() {
		return toString();
	}
}

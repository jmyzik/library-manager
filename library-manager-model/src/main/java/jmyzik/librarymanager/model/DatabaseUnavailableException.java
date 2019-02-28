package jmyzik.librarymanager.model;

public class DatabaseUnavailableException extends Exception {

	@Override
	public String getMessage() {
		return "The database is unavailable";
	}
	
}

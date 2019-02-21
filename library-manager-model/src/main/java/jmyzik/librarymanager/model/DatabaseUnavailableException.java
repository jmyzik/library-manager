package jmyzik.librarymanager.model;

public class DatabaseUnavailableException extends Exception {

	@Override
	public String getMessage() {
		return "Baza danych jest niedostêpna";
	}
	
}

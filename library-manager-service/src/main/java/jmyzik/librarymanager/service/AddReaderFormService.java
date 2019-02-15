package jmyzik.librarymanager.service;

import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.Database;

public class AddReaderFormService {

	public void addReader(Reader reader) {
		Database.INSTANCE.addReader(reader);
	}
}

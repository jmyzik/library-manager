package jmyzik.librarymanager.controller;

import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.service.AddReaderFormService;

public class AddReaderFormController {
	private AddReaderFormService addReaderFormService;
	
	public AddReaderFormController() {
		addReaderFormService = new AddReaderFormService();
	}

	public void addReader(Reader reader) throws DatabaseUnavailableException {
		addReaderFormService.addReader(reader);
	}
}

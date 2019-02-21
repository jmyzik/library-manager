package jmyzik.librarymanager.service;

import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;
import jmyzik.librarymanager.query.AddReaderFormQuery;

public class AddReaderFormService {
	
	AddReaderFormQuery query;
	
	public AddReaderFormService() {
		query = new AddReaderFormQuery();
	}

	public void addReader(Reader reader) throws DatabaseUnavailableException {
		query.addReader(reader);
	}
}

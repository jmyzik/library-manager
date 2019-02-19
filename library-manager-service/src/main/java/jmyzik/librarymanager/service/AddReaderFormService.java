package jmyzik.librarymanager.service;

import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.query.AddReaderFormQuery;

public class AddReaderFormService {
	
	AddReaderFormQuery query;
	
	public AddReaderFormService() {
		query = new AddReaderFormQuery();
	}

	public void addReader(Reader reader) {
		query.addReader(reader);
	}
}

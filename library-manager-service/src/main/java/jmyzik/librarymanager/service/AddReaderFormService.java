package jmyzik.librarymanager.service;

import jmyzik.librarymanager.dao.ReadersDAO;
import jmyzik.librarymanager.domain.Reader;
import jmyzik.librarymanager.model.DatabaseUnavailableException;

public class AddReaderFormService {
	
	ReadersDAO readersDAO;
	
	public AddReaderFormService() {
		readersDAO = new ReadersDAO();
	}

	public void addReader(Reader reader) throws DatabaseUnavailableException {
		readersDAO.addReader(reader);
	}
}

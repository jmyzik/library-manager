package jmyzik.librarymanager.query;

import jmyzik.librarymanager.model.EntityManagerHandler;

public class AbstractQuery {

	public void open() {
		EntityManagerHandler.INSTANCE.open();
	}
	
	public void shutdown() {
		EntityManagerHandler.INSTANCE.shutdown();
	}
}

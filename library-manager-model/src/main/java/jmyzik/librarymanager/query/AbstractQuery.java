package jmyzik.librarymanager.query;

import jmyzik.librarymanager.model.EntityManagerHandler;

public class AbstractQuery {

	protected EntityManagerHandler entityManagerHandler = EntityManagerHandler.INSTANCE;
	
	public void open() {
		if (entityManagerHandler.isAvailable()) {
			EntityManagerHandler.INSTANCE.open();			
		}
	}
	
	public void shutdown() {
		if (entityManagerHandler.isAvailable()) {
			EntityManagerHandler.INSTANCE.shutdown();
		}
	}
}

package jmyzik.librarymanager.model;

import java.util.ArrayList;
import java.util.List;

import jmyzik.librarymanager.domain.Book;
import jmyzik.librarymanager.domain.Reader;

// Temporary class - to be replaced with a real database in the future
public enum Database {
	
	INSTANCE;
	
	private List<Book> bookList;
	private List<Reader> readerList;
	
	private Database() {
		// Some data to experiment with
		bookList = new ArrayList<>();
		Book book = new Book(1, "ABC", "Agatha Christie", "Wydawnictwo Dolnoœl¹skie", 2015);
		bookList.add(book);
		book = new Book(2, "Cz³owiek w poszukiwaniu sensu", "Viktor E. Frankl", "Czarna Owca", 2011);
		bookList.add(book);
		book = new Book(3, "Co wie Twój kot?", "Sally Morgan", "ALMA-PRESS", 2015);
		bookList.add(book);
		book = new Book(4, "Inny œwiat", "Gustaw Herling-Grudziñski", "Wydawnictwo Literackie", 2007);
		bookList.add(book);
		
		readerList = new ArrayList<>();
		Reader reader = new Reader(1, "Pawe³", "Nowak", "ul. Prosta 3c, Warszawa");
		readerList.add(reader);
		reader = new Reader(2, "Anna", "Kowalska", "ul. Koœciuszki 110/11, Warszawa");
		readerList.add(reader);
		reader = new Reader(3, "Micha³", "Malinowski", "ul. Szeroka 129/5, Warszawa");
		readerList.add(reader);
	}
	
	public List<Book> getAllBooks() {
		return bookList;
	}
	
	public List<Reader> getAllReaders() {
		return readerList;
	}
	
	public void addBook(Book book) {
		bookList.add(book);
	}
	
	public void addReader(Reader reader) {
		readerList.add(reader);
	}
	
	public boolean removeBook(Book book) {
		return bookList.remove(book);
	}
	
	public boolean removeReader(Reader reader) {
		return readerList.remove(reader);
	}
}

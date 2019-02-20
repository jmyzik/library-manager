package jmyzik.librarymanager.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.GenerationType;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@TableGenerator(name = "mojGenerator", 
//		table = "tabela_z_identyfikatorami",
//		pkColumnName = "nazwa_sekwencji",
//		valueColumnName = "wartosc_identyfikatora",
//		pkColumnValue = "id_pracownika",
//		initialValue = 50,
//		allocationSize = 15)
	private long id;
	private String title;
	private String author;
	private String publisher;
	private int publicationYear;
	
	public Book() {}

	public Book(String title, String author, String publisher, int publicationYear) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publicationYear = publicationYear;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
}
package jmyzik.librarymanager.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BorrowTransaction {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private Reader borrower;
	@ManyToOne
	private Book book;
	@Column(nullable = false)
	private LocalDate borrowDate;
	@Column(nullable = false)
	private LocalDate returnDate;
	
	public BorrowTransaction() {}
	
	public BorrowTransaction(Reader borrower, Book book, LocalDate borrowDate, LocalDate returnDate) {
		this.borrower = borrower;
		this.book = book;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
	}

	public long getId() {
		return id;
	}

	public Reader getBorrower() {
		return borrower;
	}

	public void setBorrower(Reader borrower) {
		this.borrower = borrower;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
}
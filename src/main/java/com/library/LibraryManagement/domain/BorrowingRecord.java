package com.library.LibraryManagement.domain;

import java.util.Date;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="BORROWING_RECORD")
public class BorrowingRecord { 
	
	@Id
	@Column(name="borrowing_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long borrowingId;
	
	@Column(name="borrowing_date")
	private Date borrowingDate;
	
	@Column(name="return_date")
	private Date returnDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="book_id", nullable=false)
	private Book book;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="patron_id", nullable=false)
	private Patron patron;
		
	public Date getBorrowingDate() {
		return borrowingDate;
	}
	public void setBorrowingDate(Date borrowingDate) {
		this.borrowingDate = borrowingDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Long getBorrowingId() {
		return borrowingId;
	}
	public void setBorrowingId(Long borrowingId) {
		this.borrowingId = borrowingId;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public Patron getPatron() {
		return patron;
	}
	public void setPatron(Patron patron) {
		this.patron = patron;
	}
	@Override
	public int hashCode() {
		return Objects.hash(book, borrowingDate, borrowingId, patron, returnDate);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BorrowingRecord other = (BorrowingRecord) obj;
		return Objects.equals(book, other.book) && Objects.equals(borrowingDate, other.borrowingDate)
				&& Objects.equals(borrowingId, other.borrowingId) && Objects.equals(patron, other.patron)
				&& Objects.equals(returnDate, other.returnDate);
	}
	
	@Override
	public String toString() {
		return "BorrowingRecord [borrowingId=" + borrowingId + ", borrowingDate=" + borrowingDate + ", returnDate="
				+ returnDate + "]";
	}
		
}
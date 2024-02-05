package com.library.LibraryManagement.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name="BOOK")
public class Book implements Serializable { 
		
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="title")
	@Length(min=1,max=50,message="Title cannot exceed 150 characters.")
	@NotBlank(message="Please enter the title of the book.")
	private String title;
	
	@Column(name="author")
	@Length(min=1,max=50,message="Author name cannot exceed 50 characters.")
	@NotBlank(message="Please enter the author name.")
	private String author;
	
	@Column(name="publication_year")
	private int publicationYear;
	
	@Column(name="isbn")
	@Length(min=13,max=13,message="ISBN must have 13 digits.")
	@NotBlank(message="Please enter the 13 digit ISBN.")
	private String isbn;
	
	@OneToMany(mappedBy="book",cascade = CascadeType.REMOVE)
	private List<BorrowingRecord> borrowingRecords;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public int getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public Book addBorrowingRecord(BorrowingRecord borrowingRecord) {
		borrowingRecords.add(borrowingRecord);
		borrowingRecord.setBook(this);
		return this;
	}
	@Override
	public int hashCode() {
		return Objects.hash(author, borrowingRecords, id, isbn, publicationYear, title);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(borrowingRecords, other.borrowingRecords)
				&& Objects.equals(id, other.id) && Objects.equals(isbn, other.isbn)
				&& publicationYear == other.publicationYear && Objects.equals(title, other.title);
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", publicationYear=" + publicationYear
				+ ", isbn=" + isbn + ", borrowingRecords=" + borrowingRecords + "]";
	}

		
}
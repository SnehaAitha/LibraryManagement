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
@Table(name="PATRON")
public class Patron implements Serializable { 	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name")
	@Length(min=1,max=50,message="Name cannot exceed 50 characters.")
	@NotBlank(message="Please enter patron name.")
	private String name;
	
	@Column(name="contact_information")
	@Length(min=1,max=150,message="Patron Contact Information cannot exceed 150 characters.")
	@NotBlank(message="Please enter patron contact information.")
	private String contactInfo;
	
	@OneToMany(mappedBy="patron",cascade = CascadeType.REMOVE)
	private List<BorrowingRecord> borrowingRecords;
	
	public Long getId() {
		return id;
	}
	public void setID(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}	
	public Patron addBorrowingRecord(BorrowingRecord borrowingRecord) {
		borrowingRecords.add(borrowingRecord);
		borrowingRecord.setPatron(this);
		return this;
	}
	@Override
	public int hashCode() {
		return Objects.hash(borrowingRecords, contactInfo, id, name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patron other = (Patron) obj;
		return Objects.equals(borrowingRecords, other.borrowingRecords)
				&& Objects.equals(contactInfo, other.contactInfo) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
	
	@Override
	public String toString() {
		return "Patron [id=" + id + ", name=" + name + ", contactInfo=" + contactInfo + ", borrowingRecords="
				+ borrowingRecords + "]";
	}
	
}
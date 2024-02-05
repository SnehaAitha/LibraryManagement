CREATE TABLE book (
    id int PRIMARY KEY,
    title varchar(150),
    author varchar(50),
	publication_year int,
    isbn varchar(20)
 );

CREATE TABLE patron (
    id int PRIMARY KEY,
    name varchar(50),
    contact_information varchar(150)
 );
 
CREATE TABLE borrowing_record (
    borrowing_id int PRIMARY KEY,
    borrowing_date date,
    return_date date,
    book_id int,
    patron_id int
 );
   
 ALTER TABLE borrowing_record ADD FOREIGN KEY (PATRON_ID) REFERENCES patron(id);
 ALTER TABLE borrowing_record ADD FOREIGN KEY (BOOK_ID) REFERENCES book(id);
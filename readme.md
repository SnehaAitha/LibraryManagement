                                                                           LIBRARY MANAGEMENT PROJECT

Tech Stack:

● Java
● Spring Boot
● Spring Data JPA
● Rest API
● H2 DB for DB Storage

Additional Features:

● JWT Security.
● Junit Testing.
● Spring Boot caching mechanism using @Cacheable.(Used for fetching one or all book and patron details)
● Input validation for API requests and Exception handling with appropriate status codes.
● Transaction Management using @Transactional.

1.) HOW TO RUN THE APPLICATION?
-> Right Click on the project, Run As Maven Build with goal "spring-boot:run".

2.) HOW TO INTERACT WITH API requests?

-> All the API requests are protected and authenticated using JWT Security.
-> Get the JWT token - POST http://localhost:8080/api/authenticate
-> Open Rest Client, go to Authorization section, select type "Bearer Token" and enter the JWT token.

Book management endpoints:

● Retrieve a list of all books. - GET http://localhost:8080/api/books
● Retrieve details of a specific book by ID. - GET http://localhost:8080/api/books/{id}
● Add a new book to the library. - POST http://localhost:8080/api/books
● Update an existing book's information. - PUT http://localhost:8080/api/books/{id}
● Remove a book from the library. - DELETE http://localhost:8080/api/books/{id}

Patron management endpoints:

● Retrieve a list of all patrons. - GET http://localhost:8080/api/patrons
● Retrieve details of a specific patron by ID. - GET http://localhost:8080/api/patrons/{id}
● Add a new patron to the system. - POST http://localhost:8080/api/patrons
● Update an existing patron's information. - PUT http://localhost:8080/api/patrons/{id}
● Remove a patron from the system. - DELETE http://localhost:8080/api/patrons/{id}

Borrowing endpoints:

● Allow a patron to borrow a book. - POST http://localhost:8080/api/borrow/{bookId}/patron/{patronId}
● Record the return of a borrowed book by a patron. - PUT http://localhost:8080/api/return/{bookId}/patron/{patronId}

3.) HOW TO INTERACT WITH H2 DB?

-> Go to http://localhost:8080/api/h2-console/.
-> Enter the below credentials.

username: sa
password: password

4.) HOW TO RUN JUNIT TESTS?
-> Right Click on the project, Run As Junit Test.


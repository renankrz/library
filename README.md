# Library

A command-line application for managing a collection of books, authors, and tags. Built with Spring Boot, Spring Shell, and JPA.

## Features

- Add, list, and remove books, authors, and tags
- Update book details (name, year, edition, authors, tags)
- Interactive shell commands for easy management
- PostgreSQL database support
- Native image build profile (GraalVM)

## Requirements

- Java 17+
- Maven 3.8+
- PostgreSQL (or compatible database)

## Getting Started

### Clone the repository

```sh
git clone <repo-url>
cd library
```

### Configure the database

Edit `config/application.properties` with your database credentials. Example:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/library
spring.datasource.username=youruser
spring.datasource.password=yourpassword
```

### Build the project

```sh
./mvnw clean package
```

### Run the application

```sh
./mvnw spring-boot:run
```

### Using the Shell

Once running, you can use commands like:

- `add` — Add a new book
- `books` — List books
- `authors` — List authors
- `tags` — List tags
- `rm` — Remove a book, author, or tag
- `fix` — Update book or author details interactively

Use `help` in the shell for more details on each command.

## Native Image (GraalVM)

To build a native image:

```sh
./mvnw -Pnative package
```

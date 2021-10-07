package br.com.taha.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.taha.bookservice.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}

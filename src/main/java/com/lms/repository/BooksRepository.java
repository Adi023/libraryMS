package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {

}

package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.entity.Members;

@Repository
public interface MembersRepository extends JpaRepository<Members, Long> {

}

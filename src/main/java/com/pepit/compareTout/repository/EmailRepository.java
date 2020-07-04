package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}

package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.ComparisonType;
import com.pepit.compareTout.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComparisonTypeRepository extends JpaRepository<ComparisonType, Long> {
}

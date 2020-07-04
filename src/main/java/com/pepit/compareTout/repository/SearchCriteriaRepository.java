package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.SearchCriteria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchCriteriaRepository extends JpaRepository<SearchCriteria, Long> {
}

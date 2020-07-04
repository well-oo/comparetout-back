package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.Criteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaRepository extends JpaRepository<Criteria, Long> {
    Criteria findByName(String name);
}

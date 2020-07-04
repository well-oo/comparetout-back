package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.ComparisonTypeRecord;
import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComparisonTypeRecordWithEnumRepository extends JpaRepository<ComparisonTypeRecordWithEnum, Long> {
}

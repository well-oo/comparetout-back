package com.pepit.compareTout.repository;

import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnum;
import com.pepit.compareTout.entity.ComparisonTypeRecordWithEnumValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComparisonTypeRecordWithEnumValueRepository extends JpaRepository<ComparisonTypeRecordWithEnumValue, Long> {
}

package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class ComparisonTypeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComparisonTypeRecord;

    public ComparisonTypeRecord() {
    }

}

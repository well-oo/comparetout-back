package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SearchCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSearchCriteria;

    @ManyToOne
    private Criteria criteria;

    @ManyToOne
    private CriteriaImportance criteriaImportance;

    @ManyToOne
    private ComparisonTypeRecord comparisonTypeRecord;

    public SearchCriteria() {
    }

    public SearchCriteria(Criteria criteria, CriteriaImportance criteriaImportance,ComparisonTypeRecord comparisonTypeRecord) {
        this.criteria = criteria;
        this.criteriaImportance = criteriaImportance;
        this.comparisonTypeRecord = comparisonTypeRecord;
    }
}

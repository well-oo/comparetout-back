package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class CriteriaImportance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCriteriaImportance;

    private Integer orderOfImportance;

    private Integer weight;

    public CriteriaImportance() {
    }

    public CriteriaImportance(Integer orderOfImportance, Integer weight) {
        this.orderOfImportance = orderOfImportance;
        this.weight = weight;
    }
}

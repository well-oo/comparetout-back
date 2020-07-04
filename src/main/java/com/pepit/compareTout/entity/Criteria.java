package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Criteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCriteria;

    private String name;

    private String mesure;

    private Boolean isCriteriaListDynamical;

    private boolean estObligatoire;

    /*@ManyToOne
    private Mesure mesure;*/

    @ManyToOne
    private ComparisonMethod comparisonMethod;

    public Criteria(){

    }

    public Criteria(String name, Boolean isCriteriaListDynamical, Boolean estObligatoire, String mesure, ComparisonMethod comparisonMethod) {
        this.name = name;
        this.isCriteriaListDynamical = isCriteriaListDynamical;
        this.estObligatoire = estObligatoire;
        this.mesure = mesure;
        this.comparisonMethod = comparisonMethod;
    }

}

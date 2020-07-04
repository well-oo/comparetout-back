package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class ValueCriteriaProduct implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idValueCriteriaProduct;

    @JoinColumn(name="id_criteria")
    @ManyToOne
    private Criteria criteria;


    @Column(length=30000)
    private String value;

    public ValueCriteriaProduct() {
    }

    public ValueCriteriaProduct(Criteria criteria, String value) {
        this.criteria = criteria;
        this.value = value;
    }
}

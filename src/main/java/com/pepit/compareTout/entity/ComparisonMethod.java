package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ComparisonMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComparisonMethod;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ComparisonType comparisonType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DataType dataType;

    public ComparisonMethod() {
    }

    public ComparisonMethod(ComparisonType comparisonType) {
        this.comparisonType = comparisonType;
    }

    public ComparisonMethod(DataType dataType) {
        this.dataType = dataType;
    }

    public ComparisonMethod(ComparisonType comparisonType,DataType dataType) {
        this.comparisonType = comparisonType;
        this.dataType = dataType;
    }

}

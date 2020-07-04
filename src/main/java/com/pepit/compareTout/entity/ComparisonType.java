package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ComparisonType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComparisonType;

    private String name;

    public ComparisonType() {
    }

    public ComparisonType(String name) {
        this.name = name;
    }
}

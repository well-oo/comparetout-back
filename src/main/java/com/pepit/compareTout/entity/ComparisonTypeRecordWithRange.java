package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("RANGE")
public class ComparisonTypeRecordWithRange extends ComparisonTypeRecord{

    private float leftOperande;
    private float rightOperande;

    public ComparisonTypeRecordWithRange(float leftOperande,float rightOperande) {
        this.leftOperande=leftOperande;
        this.rightOperande=rightOperande;
    }
}

package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ComparisonTypeRecordWithEnumValue {

    @Id
    private Long idComparisonTypeRecordWithEnumValue;

    private String value;

    public ComparisonTypeRecordWithEnumValue(String value){
        this.value=value;
    }
}

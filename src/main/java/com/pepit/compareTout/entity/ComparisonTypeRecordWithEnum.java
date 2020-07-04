package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@DiscriminatorValue("ENUM")
public class ComparisonTypeRecordWithEnum extends ComparisonTypeRecord{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComparisonTypeRecordWithEnum;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ComparisonTypeRecordWithEnumValue> values;

    public ComparisonTypeRecordWithEnum(List<ComparisonTypeRecordWithEnumValue> values) {
        this.values=values;
    }

}

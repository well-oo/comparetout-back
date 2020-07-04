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
public class EmailType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmailType;

    private String name;

    public EmailType() {
    }

    public EmailType(String name) {
        this.name=name;
    }

}

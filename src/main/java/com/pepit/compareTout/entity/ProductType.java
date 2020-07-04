package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProductType;

    private String name;

    private String headerImage;

    private String favicon;

    private String titleWebPage;

    @ManyToMany
    private List<Criteria> criteriaList;

    public ProductType(){}

    public ProductType(String name, String headerImage, String favicon, String titleWebPage) {
        this.name = name;
        this.headerImage = headerImage;
        this.favicon = favicon;
        this.titleWebPage = titleWebPage;
    }

    public ProductType(String name, String headerImage, String favicon, String titleWebPage, List<Criteria> criteriaList) {
        this.name = name;
        this.headerImage = headerImage;
        this.favicon = favicon;
        this.titleWebPage = titleWebPage;
        this.criteriaList = criteriaList;
    }
}

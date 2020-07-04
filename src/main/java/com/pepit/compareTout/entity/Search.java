package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
public class Search {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSearch;

    private Instant date;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SearchCriteria> searchCriterias;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> productsResult;

    public Search() {
    }

    public Search(Instant date,List<SearchCriteria> searchCriterias) {
        this.date = date;
        this.searchCriterias=searchCriterias;
    }
}
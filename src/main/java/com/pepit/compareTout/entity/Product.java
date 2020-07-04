package com.pepit.compareTout.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    private String picture;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<ValueCriteriaProduct> valueCriteriaProductList;

    @Lob
    @Column( length = 1000 )
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant addProduct;

    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    @JoinColumn(name = "id_product_type")
    private ProductType productType;

    @ManyToOne
    private User user;

    private String name;

    public Product(){

    }

    public Product(String picture,List<ValueCriteriaProduct> valueCriteriaProductList, String description, Instant addProduct, ProductType productType, User user, String name) {
        this.picture = picture;
        this.valueCriteriaProductList = valueCriteriaProductList;
        this.description = description;
        this.addProduct = addProduct;
        this.productType = productType;
        this.user = user;
        this.name = name;
    }

    // @ManyToOne
  //  private Fournisseur fournisseur;

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", addProduct=" + addProduct +
                ", productType=" + productType +
                '}';
    }

}

package com.pepit.compareTout.entity;

import lombok.Getter;

@Getter
public class UserWithProduct {
    User user;
    Long nbProduits;

    public UserWithProduct(User user, Long nbProduits){
        this.user = user;
        this.nbProduits = nbProduits;
    }
}

package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSubscription;

    @ManyToOne
    private Email email;

    @ManyToOne
    private Search search;

    public Subscription() {
    }

    public Subscription(Email email, Search search) {
        this.email = email;
        this.search = search;
    }

    public Long getIdSubscription() {
        return idSubscription;
    }

    public void setIdSubscription(Long idSubscription) {
        this.idSubscription = idSubscription;
    }

    //REDONDANCE EN V2

}

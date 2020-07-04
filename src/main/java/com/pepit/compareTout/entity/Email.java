package com.pepit.compareTout.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Getter
@Setter
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmail;

    private String object;
    private String fromWho;
    private String toWho;
    private Instant sendDate;

    @ManyToOne
    private EmailType emailType;

    public Email() {
    }

    public Email(String object, String fromWho, String toWho, Instant sendDate, EmailType emailType) {
        this.object = object;
        this.fromWho = fromWho;
        this.toWho = toWho;
        this.sendDate = sendDate;
        this.emailType = emailType;
    }

    public Email(Long idEmail,String object, String fromWho, String toWho, Instant sendDate, EmailType emailType) {
        this.idEmail = idEmail;
        this.object = object;
        this.fromWho = fromWho;
        this.toWho = toWho;
        this.sendDate = sendDate;
        this.emailType = emailType;
    }
}

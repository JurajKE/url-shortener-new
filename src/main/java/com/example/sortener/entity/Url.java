package com.example.sortener.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Url {

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_JUST_FOR_TEST", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    private int id;
    private String originalUrl;
    private String shortUrl;
    private int calls;
    private int redirectType;
    @ManyToOne
    @JoinColumn(name="account_id",referencedColumnName = "id")
    private Account accountId;

}

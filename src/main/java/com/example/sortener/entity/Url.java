package com.example.sortener.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Url {

    @Id
    private int id;
    private String originalUrl;
    private String shortUrl;
    private int calls;
    private int redirectType;
    @ManyToOne
    @JoinColumn(name="account_id",referencedColumnName = "id")
    private Account accountId;

}

package com.example.sortener.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Account {

    @Id
    private Integer id;
    private String accountId;
    private String password;
    @OneToMany(mappedBy="accountId")
    List<Url> urlList = new ArrayList<>();

}

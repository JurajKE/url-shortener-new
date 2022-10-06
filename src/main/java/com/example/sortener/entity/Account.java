package com.example.sortener.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
public class Account {

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_JUST_FOR_TEST", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    private Integer id;
    private String accountId;
    private String password;
    @OneToMany(mappedBy="accountId", cascade = ALL)
    List<Url> urlList;

}

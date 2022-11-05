package com.example.sortener.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @SequenceGenerator(name="SEQ_GEN", sequenceName="SEQ_JUST_FOR_TEST", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    private Integer id;
    @NonNull
    private String accountId;
    @NonNull
    private String password;
    @OneToMany(mappedBy="accountId", cascade = ALL)
    List<Url> urlList;

}

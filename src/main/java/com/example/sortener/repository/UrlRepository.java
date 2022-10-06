package com.example.sortener.repository;

import com.example.sortener.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlRepository extends JpaRepository<Url, Integer> {

//    @Query("SELECT Url FROM Url url WHERE url.accountId = ?1")
//    Url findByAccountId(String accountId);

    List<Url> findByAccountId_AccountId(String accountId);

}

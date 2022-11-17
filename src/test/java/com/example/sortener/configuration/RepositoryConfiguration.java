package com.example.sortener.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public abstract class RepositoryConfiguration {

    @Autowired
    private TestEntityManager entityManager;

    public void runSQL(String sql) {
        entityManager.getEntityManager().createNativeQuery(sql).executeUpdate();
    }

    public void runSQL(String sql, Object... params) {
        String formatted = String.format(sql, params);
        entityManager.getEntityManager().createNativeQuery(formatted).executeUpdate();
    }

}

package com.epam.billing.repository;


import java.sql.Connection;
import java.util.List;

public abstract class AbstractRepository<T> {

    private final ConnectionManager connectionManager = new ConnectionManager();

    protected Connection getConnection() {
        return connectionManager.getConnection();
    }

    public abstract List<T> getAll();

    public abstract T getById(int id);

    public abstract T save(T t);

    public abstract T update(T t);

    public abstract boolean delete(T t);

    public abstract boolean existById(long id);
}

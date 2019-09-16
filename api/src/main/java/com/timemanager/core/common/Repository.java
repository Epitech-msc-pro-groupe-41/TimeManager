package com.timemanager.core.common;

import com.mongodb.client.result.UpdateResult;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

public interface Repository<D> {

    void create(D document);

    void update(D document);
    
    UpdateResult update(Query query, Update update);

    void delete(D document);

    void delete(String key, String value);

    D findById(String id);

    D findByRef(String ref);

    List<D> find(String key, String value);

    List<D> find(Map<String, String> map);
    
    public List<D> find(Query query);

    List<D> findAll();

    void dropCollection();

    void dropCollection(String collectionName);
}
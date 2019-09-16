package com.timemanager.core.common;


import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractRepository<D> implements Repository<D> {

    @Autowired
    private MongoTemplate mongoTemplate;

    public static final String REF = "ref";

    public void create(D application) {
        mongoTemplate.save(application);
    }

    public void update(D application) {
        mongoTemplate.save(application);
    }
    
    public UpdateResult update(Query query, Update update) {
        return mongoTemplate.updateFirst(query, update, getDocumentClass());
   }

    public void delete(D application) {
        mongoTemplate.remove(application);
    }

    public void delete(String key, String value) {
        mongoTemplate.remove(Query.query(Criteria.where(key).is(value)), getDocumentClass());
    }


    public D findById(String id) {
        return mongoTemplate.findById(new ObjectId(id), getDocumentClass());
    }

    public D findByRef(String ref) {
        return mongoTemplate.findOne(Query.query(Criteria.where(REF).is(ref)), getDocumentClass());
    }

    public List<D> find(String key, String value) {
        return mongoTemplate.find(Query.query(Criteria.where(key).is(value)), getDocumentClass());
    }
    
    public long count(Query query) {
        return mongoTemplate.count(query, getDocumentClass());
    }

    public List<D> find(Query query) {
        return mongoTemplate.find(query,getDocumentClass());
    }

    public List<D> find(Map<String, String> map) {
        Criteria criteria = new Criteria();
        for (String key : map.keySet()) {
            Criteria nestedCriteria = Criteria.where(key).is(map.get(key));
            criteria.andOperator(nestedCriteria);
        }
        return mongoTemplate.find(Query.query(criteria), getDocumentClass());
    }

    public void dropAllCollections() {
        Set<String> collections = mongoTemplate.getCollectionNames();
        for(String collection : collections){
            mongoTemplate.dropCollection(collection);
        }
    }
    
    public List<D> findAll() {
        return mongoTemplate.findAll(getDocumentClass());
    }
    
    public void dropCollection(String collectionName) {
        mongoTemplate.dropCollection(collectionName);
    }

    public void dropCollection() {
        mongoTemplate.dropCollection(getDocumentClass());
    }

    @SuppressWarnings("unchecked")
    private Class<D> getDocumentClass() {
        final Type classType = this.getClass().getGenericSuperclass();
        final ParameterizedType parameterizedType = (ParameterizedType) classType;
        return (Class<D>) parameterizedType.getActualTypeArguments()[0];
    }

}

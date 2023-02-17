package apicore.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;


@Component
public class MongoRepository {
    
    @Autowired private MongoTemplate mongoTemplate;

    public <T> T insert(T entity){
        return mongoTemplate.insert(entity);
    }

    public <T> T update(T entity){
        return mongoTemplate.save(entity);
    }

    public <T> T getOneById(String id, Class<T> entityClass){
        return mongoTemplate.findById(id, entityClass);
    }


    public  <T> T getOneByAttrEqual(String attrName, String valueToSearch, Class<T> entityClass){
        return mongoTemplate.findOne(new Query(Criteria.where(attrName).is(valueToSearch)), entityClass);
    }

    public long removeOneByAttrEqual(String attrName, String valueToSearch, Class<?> entityClass){
        return mongoTemplate.remove(new Query(Criteria.where(attrName).is(valueToSearch)), entityClass).getDeletedCount();
    }

    public long removeById(String id, Class<?> entityClass){
        return removeOneByAttrEqual("id", id, entityClass);
    }

    public boolean existsByAttrEqual(String attrName, String valueToSearch, Class<?> entityClass){
        return mongoTemplate.exists(new Query(Criteria.where(attrName).is(valueToSearch)), entityClass);
    }

    public <T> List<T> list(Class<T> entityClass, int pageNum, int pageSize){
        Query query = new Query();
        query.skip(pageNum);
        query.limit(pageSize);
        return mongoTemplate.find(query, entityClass);
    }

}

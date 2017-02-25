package com.homeenv.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ImageClassesRepository  {

    @PersistenceContext
    private EntityManager entityManager;


    public List<String> findOrderedPossibleClassifications(){
        return entityManager
                .createNativeQuery("select classes from (select distinct on(1) image_id, classes, score, id from image_classifications order by image_id, score desc) cls group by classes")
                .getResultList();
    };
}

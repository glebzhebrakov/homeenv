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
                .createNativeQuery("select classes from image_classifications group by classes ")
                .getResultList();
    };
}

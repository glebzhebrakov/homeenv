package com.homeenv.repository;


import com.homeenv.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findByHash(String hash);

    @Query("from Image img where img.indexed = true")
    List<Image> findClassified();
}

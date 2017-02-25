package com.homeenv.repository;


import com.homeenv.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    Optional<Image> findByHash(String hash);

    @Query("from Image img where img.indexed = true")
    List<Image> findClassified();

    @Query("from Image img where img.indexed = true")
    Page<Image> findClassifiedPageable(Pageable pageRequest);

    @Query("select img from Image as img right join img.imageClassifications as imgc where imgc.classes like %:cls% and imgc.score >= 0.1")
    Page<Image> findClassifiedPageableByClass(@Param("cls") String cls, Pageable pageRequest);
}

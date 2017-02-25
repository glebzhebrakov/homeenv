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

    @Query(value = "select img.id, img.path,img.indexed,img.hash,img.mime,img.error from ((select * from image) img right join (select distinct on(1) image_id, classes, score, id from image_classifications order by image_id, score desc) cls on img.id=cls.image_id) where cls.classes like %:cls%", nativeQuery = true)
    List<Image> findClassifiedPageableByClass(@Param("cls") String cls);
}

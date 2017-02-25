package com.homeenv.repository;

import com.homeenv.domain.ImageClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageClassificationRepository extends JpaRepository<ImageClassification, Long> {

}

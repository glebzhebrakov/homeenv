package com.homeenv.domain;

import javax.persistence.*;

@Entity
@Table(name = "image_classifications")
public class ImageClassification implements Comparable<ImageClassification> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn (name = "image_id")
    private Image image;

    private String classes;
    private float score;

    public ImageClassification(Image image, String classes, float score) {
        this.image = image;
        this.classes = classes;
        this.score = score;
    }

    public ImageClassification(String classes, float score) {
        this.classes = classes;
        this.score = score;
    }

    public ImageClassification() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int compareTo(ImageClassification imageClassification) {
        if (this.getScore() < imageClassification.getScore()) return 1;
        if (this.getScore() > imageClassification.getScore()) return -1;
        return 0;
    }
}

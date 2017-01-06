package com.homeenv.domain;

import javax.persistence.*;

@Entity
@Table(name = "image_duplicate")
public class ImageDuplicate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String path;

    public ImageDuplicate() {

    }

    public ImageDuplicate(String path) {
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

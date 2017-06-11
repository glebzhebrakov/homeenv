package com.homeenv.domain;

import javax.persistence.*;

@Entity
@Table(name = "image_attribute")
public class ImageAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String value;

    @ManyToOne
    @JoinColumn (name = "image_id")
    private Image image;

    public ImageAttribute() {
    }

    public ImageAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}

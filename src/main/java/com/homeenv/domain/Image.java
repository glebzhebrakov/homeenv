package com.homeenv.domain;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String path;
    private Boolean indexed;
    private String hash;
    private String mime;
    private String error;

    @OneToMany
    private Set<ImageAttribute> attributes;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ImageClassification> imageClassifications;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ImageDuplicate> duplicates;

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

    public Boolean getIndexed() {
        return indexed;
    }

    public void setIndexed(Boolean indexed) {
        this.indexed = indexed;
    }

    public Set<ImageAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<ImageAttribute> attributes) {
        this.attributes = attributes;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public Image withHash(String hash){
        setHash(hash);
        return this;
    }

    public Image withPath(String path){
        setPath(path);
        return this;
    }

    public Image withIndexed(Boolean indexed){
        setIndexed(indexed);
        return this;
    }

    public Image withMime(String mime){
        setMime(mime);
        return this;
    }

    public void addDuplicate(ImageDuplicate duplicate){
        if (duplicates == null){
            duplicates = new HashSet<>();
        }

        duplicates.add(duplicate);
    }

    public Set<ImageDuplicate> getDuplicates() {
        return duplicates;
    }

    public void setDuplicates(Set<ImageDuplicate> duplicates) {
        this.duplicates = duplicates;
    }

    public Set<ImageClassification> getImageClassifications() {
        return imageClassifications;
    }

    public void setImageClassifications(Set<ImageClassification> imageClassifications) {
        this.imageClassifications = imageClassifications;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

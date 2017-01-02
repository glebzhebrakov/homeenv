package com.homeenv.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "image")
public class Image {

    @Id
    private Long id;
    private String path;
    private Boolean indexed;

    @OneToMany
    private Set<ImageAttribute> attributes;

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
}

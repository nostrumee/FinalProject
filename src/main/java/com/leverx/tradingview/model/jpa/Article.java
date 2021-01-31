package com.leverx.tradingview.model.jpa;

import lombok.Data;
import javax.persistence.*;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String text;

    private Long userId;

    private Date createdAt;

    private Date updatedAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @PrePersist
    public void setCreationDate() {
        this.createdAt = new Date();
    }

}

package com.leverx.tradingview.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    List<Article> articles;

}

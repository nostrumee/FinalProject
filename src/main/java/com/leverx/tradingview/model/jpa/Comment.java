package com.leverx.tradingview.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Article articleId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User authorId;

    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    public void setCreationDate() {
        this.createdAt = new Date();
    }

}
package com.leverx.tradingview.model.jpa;

import lombok.*;

import javax.persistence.*;

import java.util.Date;

@Entity
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    private String email;

    @Column(name = "created_at")
    private Date createdAt;

    private UserRole role;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @PrePersist
    public void setCreationDate() {
        this.createdAt = new Date();
    }

}

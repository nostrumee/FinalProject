package com.leverx.tradingview.repository;

import com.leverx.tradingview.model.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void deleteById(Long id);

}

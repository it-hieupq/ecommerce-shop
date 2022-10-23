package com.capstone.client.user.domain.repository;

import com.capstone.client.user.domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUserId(Integer userId);
    User findByUsernameAndPassword(String username, String password);
    List<User> findAllByStatusTrue();

    Boolean existsByEmailOrUsername(String email, String username);
}

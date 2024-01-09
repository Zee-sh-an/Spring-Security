package com.jwt.repo;

import com.jwt.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("select u from Users u where u.email = :email")
    public Users  getByEmail(@Param("email") String email);
}

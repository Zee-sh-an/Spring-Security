package com.jwt.repo;

import com.jwt.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("select u from Users u where u.email = :email")
    public Users findByEmail(@Param("email") String email);
}

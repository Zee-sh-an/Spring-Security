package com.jwt.repo;

import com.jwt.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    @Query("select t from Token t inner join Users u on t.user.userId = u.userId where u.id = :userId and (t.expired = false or t.revoked = false)")
    List<Token> findAllValidTokensByUsers(@Param("userId") long userId);

    Optional<Token> findByToken(String token);
}

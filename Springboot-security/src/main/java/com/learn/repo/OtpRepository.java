package com.learn.repo;

import com.learn.models.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OtpRepository  extends JpaRepository<Otp,Long> {

    @Query("select o from Otp as o where o.userName = :userName")
    public Otp findByUserName(@Param("userName") String userName);
}

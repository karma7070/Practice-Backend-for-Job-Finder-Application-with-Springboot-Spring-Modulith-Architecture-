package com.FindAJob.demo.refreshtoken.internal;

import com.FindAJob.demo.refreshtoken.RefreshToken;
import com.FindAJob.demo.reg_users.Reg_Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserEmail(String email);

    RefreshToken deleteAllByUserEmail(String email);

    List<RefreshToken> findAllByUserEmail(String email);
}

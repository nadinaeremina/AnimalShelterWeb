package org.top.animalshelterwebapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByLogin(String login);
    Optional<User> findByLogin(String login);
    // Методы для управления lockout-блокировками
//    @Modifying
//    @Query("UPDATE UserDbEntity u SET u.failedAttempts = u.failedAttempts + 1 WHERE u.login = :login")
//    void incrementFailedAttempts(@Param("login") String login);
//
//    @Modifying
//    @Query("UPDATE UserDbEntity u SET u.failedAttempts = 0, u.lockedUntil = NULL WHERE u.login = :login")
//    void resetFailedAttempts(@Param("login") String login);
//
//    @Modifying
//    @Query("UPDATE UserDbEntity u SET u.lockedUntil = :lockedUntil WHERE u.login = :login")
//    void lockUser(@Param("login") String login, @Param("lockedUntil") java.time.LocalDateTime lockedUntil);
}

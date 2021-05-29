package com.nure.ua.model.repository;

import com.nure.ua.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM user WHERE phone = :userPhone")
    User selectUserByPhone(@Param("userPhone") String userPhone);

    @Query("FROM user WHERE nickname = :userNickname")
    User selectUserByNickname(@Param("userNickname") String userNickname);
}

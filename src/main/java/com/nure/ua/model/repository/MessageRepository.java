package com.nure.ua.model.repository;

import com.nure.ua.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("FROM message WHERE receiverId = :userId or senderId = :userId")
    List<Message> selectAllUserMessages(@Param("userId") long userId);
}

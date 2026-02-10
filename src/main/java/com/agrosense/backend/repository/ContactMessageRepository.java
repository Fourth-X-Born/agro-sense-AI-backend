package com.agrosense.backend.repository;

import com.agrosense.backend.entity.ContactMessage;
import com.agrosense.backend.entity.ContactMessage.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    List<ContactMessage> findAllByOrderByCreatedAtDesc();

    List<ContactMessage> findByStatusOrderByCreatedAtDesc(MessageStatus status);

    @Query("SELECT COUNT(c) FROM ContactMessage c WHERE c.status = 'NEW'")
    long countNewMessages();

    @Query("SELECT COUNT(c) FROM ContactMessage c WHERE c.status = 'READ'")
    long countReadMessages();
}

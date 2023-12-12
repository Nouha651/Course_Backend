package com.example.springbootmicroservice3.repository;

import com.example.springbootmicroservice3.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

    // Autres méthodes définies par JpaRepository...

    @Query("SELECT c FROM ChatMessage c WHERE (c.sender = :sender AND c.rec = :rec) OR (c.sender = :rec AND c.rec = :sender)")
    List<ChatMessage> findAllBySenderAndRec(String sender, String rec);
}

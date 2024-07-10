package com.example.langchain4j.repository;


import com.example.langchain4j.domain.ChatContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatContextRepository extends JpaRepository<ChatContext,Long> {

}

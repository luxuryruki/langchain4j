package com.example.langchain4j.service;

import com.example.langchain4j.domain.ChatContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public interface LangchainService {
    ChatContext create(ChatContext context);
    ChatContext update(ChatContext context);
    ChatContext read(String id);
    void delete(String id);
}

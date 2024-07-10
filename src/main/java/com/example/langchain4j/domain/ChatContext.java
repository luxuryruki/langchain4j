package com.example.langchain4j.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class ChatContext {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    String message;

    public void setMessage(String message) {
        this.message = message;
    }
}

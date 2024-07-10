package com.example.langchain4j.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class ChatContext {
    @Id
    String id;

    @Column
    String message;

    public ChatContext(String id) {
        this.id = id;
    }

    public ChatContext() {
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

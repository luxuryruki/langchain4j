package com.example.langchain4j.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;

public interface MessageAssistant {
    Response<AiMessage> chat(@MemoryId Long id, @UserMessage String userMessage);
}

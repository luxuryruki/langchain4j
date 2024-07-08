package com.example.langchain4j.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class LanchainConfguration {
    @Value("${open-ai.chat-gpt.api-key:#{null}}")
    private String apiKey;

    @Value("${open-ai.chat-gpt.chat-model:#{null}}")
    private String chatModel;

    @Value("${open-ai.chat-gpt.image-model:#{null}}")
    private String imageModel;

    @Value("${open-ai.chat-gpt.api-url-prefix:#{null}}")
    private String apiPrefix;
}

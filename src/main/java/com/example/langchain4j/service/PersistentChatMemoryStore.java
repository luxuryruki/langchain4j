package com.example.langchain4j.service;

import com.example.langchain4j.domain.ChatContext;
import com.example.langchain4j.repository.ChatContextRepository;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.Content;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.langchain4j.data.message.ChatMessageDeserializer.messagesFromJson;
import static dev.langchain4j.data.message.ChatMessageSerializer.messagesToJson;

@Component
public class PersistentChatMemoryStore implements ChatMemoryStore {

    private List<ChatMessage> list = new ArrayList<>();

    @Autowired
    private ChatContextRepository chatContextRepository;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        // TODO: Implement getting all messages from the persistent store by memory ID.
        try {
            Optional<ChatContext> chatContextOpt = chatContextRepository.findById((String) memoryId);
            if (chatContextOpt.isPresent()) {
                ChatContext context = chatContextOpt.get();
                String json = context.getMessage();
                list = messagesFromJson(json);
            }
            return list;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        // TODO: Implement updating all messages in the persistent store by memory ID.
        String json = messagesToJson(messages);
        Optional<ChatContext> chatContextOpt = chatContextRepository.findById((String) memoryId);
        if (chatContextOpt.isPresent()) {
            ChatContext context = chatContextOpt.get();
            context.setMessage(json);
            chatContextRepository.save(context);
        } else {
            ChatContext context = new ChatContext((String) memoryId);
            context.setMessage(json);
            chatContextRepository.save(context);
        }
        // ChatMessageSerializer.messageToJson(ChatMessage) and
        // ChatMessageSerializer.messagesToJson(List<ChatMessage>) helper methods can be used to
        // easily serialize chat messages into JSON.
    }

    @Override
    public void deleteMessages(Object memoryId) {
        // TODO: Implement deleting all messages in the persistent store by memory ID.
        chatContextRepository.deleteById((String) memoryId);
    }
}

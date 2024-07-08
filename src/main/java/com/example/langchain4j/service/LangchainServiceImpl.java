package com.example.langchain4j.service;

import com.example.langchain4j.config.LanchainConfguration;
import com.example.langchain4j.domain.ChatContext;
import com.example.langchain4j.repository.ChatContextRepository;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public class LangchainServiceImpl implements LangchainService{

    @Autowired
    private LanchainConfguration configuration;

    @Autowired
    ChatContextRepository repository;

    private static int maxTokens= 1000;
    private static double temperature = 1;
    private static double presencePenalty = 1;
    private static double frequencyPenalty = 0;


    @Override
    public ChatContext create(ChatContext context) {
        return repository.save(context);
    }

    @Override
    public ChatContext update(ChatContext context) {
        return null;
    }

    @Override
    public Optional<ChatContext> read(String id) {
        return repository.findById(id);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }


    public ChatLanguageModel getChatModel(){



        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(configuration.getApiKey())
                .modelName(configuration.getChatModel())
                .logRequests(true)
                .logResponses(true)
                .temperature(temperature) // 예측 가능성 0 - 2 사이 숫자 // 숫자가 낮을 수록 랜덤응답, 높을 수록 예측가능한 응답
                .maxTokens(maxTokens) // 최대 토큰 수
                .presencePenalty(presencePenalty) // 모델이 이미 사용한 단어나 문장의 반복방지 -2.0 - 2.0 사이 숫자, 숫자가 높을 수록 반복사용 안함
                .frequencyPenalty(frequencyPenalty) // 출력되는 텍스트 내 중복 단어 문장 사용 방지 -2.0 - 2.0 사이 숫자, 숫자가 높을 수록 반복사용 안함
                .build();

        return model;
    }

    public ImageModel getImageModel(String imageRatio){

        ImageModel model = OpenAiImageModel.builder()
                .apiKey(configuration.getApiKey())
                .modelName(configuration.getImageModel())
                .quality("hd")
                .size(imageRatio) // 256x256', '512x512', '1024x1024', '1024x1792', '1792x1024'
                .logRequests(true)
                .logResponses(true)
                .build();

        return model;
    }

    public MessageAssistant getMessageAssistant(ChatContext context){
        ChatMemoryProvider chatMemoryProvider = memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(20)
                .chatMemoryStore(new PersistentChatMemoryStore(context))
                .build();


        MessageAssistant assistant = AiServices.builder(MessageAssistant.class)
                .chatLanguageModel(getChatModel())
                .chatMemoryProvider(chatMemoryProvider)
                .build();


        return assistant;
    }


    public Response<AiMessage> getMessage(String id, String message){

        MessageAssistant assistant = getMessageAssistant();

        return assistant.chat(id,message);
    }
}

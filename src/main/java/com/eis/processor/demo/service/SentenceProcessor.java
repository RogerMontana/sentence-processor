package com.eis.processor.demo.service;

import lombok.RequiredArgsConstructor;
import opennlp.tools.sentdetect.SentenceDetectorME;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SentenceProcessor {

    private final Map<String, String> userSentenceRegistry = new ConcurrentHashMap<>();
    private final MessagingService messagingService;
    private final SentenceDetectorME sentenceDetectorME;

    public void processSentencesForUser(String user) {
        String[] sentences = sentenceDetectorME.sentDetect(userSentenceRegistry.get(user));
        messagingService.sendSentenceMessage(user, sentences);
        userSentenceRegistry.remove(user);
    }

    public void addWordToRegistry(String user, String word) {
        if (userSentenceRegistry.containsKey(user)) {
            String words = userSentenceRegistry.get(user);
            String updatedWords = words + word;
            userSentenceRegistry.put(user, updatedWords);
        }
        userSentenceRegistry.put(user, word);
    }

}

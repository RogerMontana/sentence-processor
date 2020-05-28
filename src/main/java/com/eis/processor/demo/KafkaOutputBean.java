package com.eis.processor.demo;

import com.eis.processor.demo.model.WordPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class KafkaOutputBean {

    public void doWork(String body) {
        System.out.println("KafkaBody result >>>>> " + this.retrieve(body));
    }

    public WordPayload retrieve(String body) {
        WordPayload message;
        try {
            message = new ObjectMapper().readValue(body, WordPayload.class);
        } catch (Exception e) {
            message = new WordPayload();
        }
        return message;
    }
}

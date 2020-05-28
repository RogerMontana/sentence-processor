package com.eis.processor.demo.service;

import com.eis.processor.demo.model.SentencePayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

@Service
public class MessagingService {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private CamelContext camelContext;

    @Qualifier("KafkaRouteProducer")
    @Autowired
    private RouteBuilder kafkaRouteProducer;

    @Qualifier("KafkaRouteConsumer")
    @Autowired
    private RouteBuilder kafkaRouteConsumer;

    @EndpointInject(uri = "direct:kafkaRoute")
    private ProducerTemplate kafkaProducer;

    private ConsumerTemplate kafkaConsumer;

    @PostConstruct
    public void setup() {
        try {
            camelContext.addRoutes(kafkaRouteProducer);
            camelContext.addRoutes(kafkaRouteConsumer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSentenceMessage(String user, String... messages) {
        Stream.of(messages)
                .map(s-> new SentencePayload(user, s))
                .forEach(this::writePayload);
    }

    private void writePayload(SentencePayload sentence) {
        try {
            kafkaProducer.sendBody("direct:kafkaRoute", mapper.writeValueAsString(sentence));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

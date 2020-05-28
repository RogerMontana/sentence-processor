package com.eis.processor.demo.config;

import com.eis.processor.demo.service.SentenceProcessor;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SentenceModelProcessorConfig {

    @Bean
    public SentenceDetectorME sentenceDetectorME() throws IOException {
        InputStream inputStream = SentenceProcessor.class.getResourceAsStream("/lib/en-sent.bin");
        SentenceModel model = new SentenceModel(inputStream);
        return new SentenceDetectorME(model);
    }

}

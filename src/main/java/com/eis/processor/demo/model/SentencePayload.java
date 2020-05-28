package com.eis.processor.demo.model;

import lombok.Value;

@Value
public class SentencePayload {
    String user;
    String sentence;
}

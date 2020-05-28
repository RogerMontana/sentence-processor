package com.eis.processor.demo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WordPayload {
    private String user;
    private String body;
}

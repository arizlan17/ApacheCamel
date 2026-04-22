package com.fileprocessor.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ContentAmendmentProcessor implements Processor {

    @Value("${file.processor.content-footer}")
    private String contentFooter;

    @Value("${file.processor.content-header}")
    private String contentHeader;

    @Override
    public void process(Exchange exchange) throws Exception {
        // Read the body as a String
        String originalContent = exchange.getIn().getBody(String.class);
        String amendedContent = contentHeader + originalContent.toUpperCase() +contentFooter;
        exchange.getIn().setBody(amendedContent);
    }
}
package com.fileprocessor.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePollingRoute extends RouteBuilder {

    @Value("${file.processor.sourceDir}")
    private String sourceDir;

    @Value("${file.processor.destination-directory}")
    private String destDir;

    @Value("${file.processor.error-directory}")
    private String errorDir;

    @Value("${file.processor.success-directory}")
    private String successDir;

    @Value("${file.processor.polling-interval}")
    private String interval;

    @Override
    public void configure() throws Exception {

        onException(Exception.class)
                .handled(true)
                .log("Error occurred: ${exception.message}")
                .to("log:error?showAll=true");



        String sourceUri = String.format("file:%s?include=.*\\.in&delay=%s&move=%s&moveFailed=%s",
                sourceDir, interval, successDir, errorDir);

        from(sourceUri)
                .routeId("fileProcessingRoute")
                .log("Picking up file: ${header.CamelFileName}")
                .log("Original content: ${body}")
                .process("contentAmendmentProcessor")
                .setHeader(Exchange.FILE_NAME,
                        simple("${file:name.noext}-${date:now:yyyy-MM-dd-HH-mm-ss}.text"))
                .to("file:" + destDir)
                .log("Successfully processed and moved to: " + destDir);
    }
}
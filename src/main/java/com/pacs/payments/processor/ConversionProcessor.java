package com.pacs.payments.processor;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConversionProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("The HTTP response code is: {}", exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
        log.info("The response body is: {}", exchange.getIn().getBody(String.class));
        String body = exchange.getIn().getBody(String.class);
        exchange.getIn().setHeader("pacsXml",body);
    }
}

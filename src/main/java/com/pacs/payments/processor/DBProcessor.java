package com.pacs.payments.processor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacs.payments.model.FetchFilePathRequest;
import com.pacs.payments.service.DBService;
import com.pacs.payments.model.FetchFilePathResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DBProcessor implements Processor {
    @Autowired
    DBService dbService;

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("The request body is: {}", exchange.getIn().getBody(String.class));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        FetchFilePathRequest fetchFilePathRequest = objectMapper.readValue(exchange.getIn().getBody(String.class), FetchFilePathRequest.class);
        FetchFilePathResponse fetchFilePathResponse = dbService.getFilePath(fetchFilePathRequest.getSourceSystem(), fetchFilePathRequest.getPaymentType());
        exchange.getIn().setHeader("pacsXsdPath", fetchFilePathResponse.getPacsXsdPath());
        exchange.getIn().setHeader("painXsdPath", fetchFilePathResponse.getPainXsdPath());
        exchange.getIn().setHeader("painXmlPath", fetchFilePathResponse.getPainXmlPath());
        exchange.getIn().setHeader("targetPacsXmlPath", fetchFilePathResponse.getTargetPacsXmlPath());
        exchange.getIn().setHeader("paymentType", fetchFilePathResponse.getPaymentType());
        exchange.getIn().setHeader("sourceSystem", fetchFilePathResponse.getSourceSystem());
    }

}

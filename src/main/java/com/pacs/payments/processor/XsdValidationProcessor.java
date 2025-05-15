package com.pacs.payments.processor;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacs.payments.model.FetchFilePathRequest;
import com.pacs.payments.model.XsdValidationModel;
import com.pacs.paymentsUtil.service.XsdValidationService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class XsdValidationProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        XsdValidationModel xsdValidationModel = objectMapper.readValue(exchange.getIn().getBody(String.class), XsdValidationModel.class);
        XsdValidationService xsdValidationService = new XsdValidationService();
        boolean result = xsdValidationService.validateXMLSchema(xsdValidationModel.getXsdPath(), xsdValidationModel.getXmlPath(), xsdValidationModel.getXmlValue() );
        if(result)
            exchange.getIn().setHeader("painXsdValidation", "success");
        else
            exchange.getIn().setHeader("painXsdValidation", "failed");
    }
}

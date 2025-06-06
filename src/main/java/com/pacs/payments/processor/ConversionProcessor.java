package com.pacs.payments.processor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacs.payments.model.ConvertorModel;
import com.pacs.payments.service.PainToPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConversionProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        ConvertorModel convertorModel = objectMapper.readValue(exchange.getIn().getBody(String.class), ConvertorModel.class);
        PainToPaymentService painToPaymentService = new PainToPaymentService();
       String pacsXml = painToPaymentService.convertToPain(convertorModel.getPaymentType(), convertorModel.getXmlPath());
       /* String pacsXml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<document xmlns:ns2=\"urn:iso:std:iso:20022:tech:xsd:pacs.002.001.10\">\n" +
                "    <ns2:FIToFIPmtStsRpt>\n" +
                "        <ns2:GrpHdr>\n" +
                "            <ns2:MsgId>1201679931</ns2:MsgId>\n" +
                "            <ns2:CreDtTm>2025-04-21T22:06:17.000000000</ns2:CreDtTm>\n" +
                "        </ns2:GrpHdr>\n" +
                "        <ns2:OrgnlGrpInfAndSts>\n" +
                "            <ns2:OrgnlMsgId>2510401959401006</ns2:OrgnlMsgId>\n" +
                "            <ns2:OrgnlMsgNmId>pacs.003.001.08</ns2:OrgnlMsgNmId>\n" +
                "            <ns2:OrgnlNbOfTxs>7</ns2:OrgnlNbOfTxs>\n" +
                "            <ns2:OrgnlCtrlSum>145.03</ns2:OrgnlCtrlSum>\n" +
                "            <ns2:GrpSts>ACCP</ns2:GrpSts>\n" +
                "        </ns2:OrgnlGrpInfAndSts>\n" +
                "    </ns2:FIToFIPmtStsRpt>\n" +
                "</document>\n";*/
        log.info("Pacs Xml  - {}", pacsXml);
        exchange.getIn().setHeader("pacsXml",pacsXml);

    }
}

package com.pacs.payments.camel;

import com.pacs.payments.model.ConvertorModel;
import com.pacs.payments.model.XsdValidationModel;
import com.pacs.payments.processor.DBProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class PaymentRoute extends RouteBuilder{
    public void configure() throws Exception {

        from("direct:fetchFileLocations")
                .log("Inside Get File Path route")
                .log("Input Body Value for getFilePaths api- "+"${body}").marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8081/xmlUtil/getFilePaths")
                .convertBodyTo(String.class)
                .process(new DBProcessor())
                .log("PacsXsdPath - "+"${header.pacsXsdPath}")
                .log("PainXsdPath - "+"${header.painXsdPath}")
                .log("PainXmlPath - "+"${header.painXmlPath}")
                .log("TargetPacsXmlPath - "+"${header.targetPacsXmlPath}")
                .process(exchange -> exchange.getIn().setBody(new XsdValidationModel(exchange.getIn().getHeader("painXsdPath", String.class),
                        exchange.getIn().getHeader("painXmlPath", String.class))))
                .to("direct:validateInputXml");
        from("direct:validateInputXml").log("Input Body Value for validateXmlWithXsd api - "+"${body}").marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8081/xmlUtil/validateXmlWithXsd")
                .process(exchange -> {
                    log.info("The HTTP response code is: {}", exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
                    log.info("The response body is: {}", exchange.getIn().getBody(String.class));
                    exchange.getIn().setBody(new ConvertorModel(exchange.getIn().getHeader("paymentType", String.class),
                            exchange.getIn().getHeader("painXmlPath", String.class)));
                })
                .to("direct:convertPainToPacs");
        from("direct:convertPainToPacs").log("Input Body Value for convertToPain api - "+"${body}").marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to("http://localhost:8081/xmlUtil/convertToPain")
                .process(exchange -> {
                    log.info("The HTTP response code is: {}", exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
                    log.info("The response body is: {}", exchange.getIn().getBody(String.class));

                });
    }

}

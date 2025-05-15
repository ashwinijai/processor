package com.pacs.payments.camel;

import com.pacs.payments.model.ConvertorModel;
import com.pacs.payments.model.MoveFileModel;
import com.pacs.payments.model.XsdValidationModel;
import com.pacs.payments.processor.ConversionProcessor;
import com.pacs.payments.processor.DBProcessor;
import com.pacs.payments.processor.MoveFileProcessor;
import com.pacs.payments.processor.XsdValidationProcessor;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentRoute extends RouteBuilder{
    @Autowired
     DBProcessor dbProcessor;
    @Autowired
    XsdValidationProcessor xsdValidationProcessor;
    @Autowired
    ConversionProcessor conversionProcessor;
    @Autowired
    MoveFileProcessor moveFileProcessor;

    public void configure() throws Exception {


        from("direct:fetchFileLocations")
                .log("Inside Get File Path route")
                .log("Input Body Value from controller - "+"${body}").marshal().json(JsonLibrary.Jackson)
                .convertBodyTo(String.class)
                .process(dbProcessor)
                .log("PacsXsdPath - "+"${header.pacsXsdPath}")
                .log("PainXsdPath - "+"${header.painXsdPath}")
                .log("PainXmlPath - "+"${header.painXmlPath}")
                .log("TargetPacsXmlPath - "+"${header.targetPacsXmlPath}")
                .process(exchange -> exchange.getIn().setBody(new XsdValidationModel(exchange.getIn().getHeader("painXsdPath", String.class),
                        exchange.getIn().getHeader("painXmlPath", String.class), null)))
                .to("direct:validateInputXml");
        from("direct:validateInputXml").log("Input Value for validateXmlWithXsd utility method - "+"${body}").marshal().json(JsonLibrary.Jackson)
                .convertBodyTo(String.class)
                .process(xsdValidationProcessor)
                .process(exchange -> exchange.getIn().setBody(new ConvertorModel(exchange.getIn().getHeader("paymentType", String.class),
                            exchange.getIn().getHeader("painXmlPath", String.class))))
                .to("direct:convertPainToPacs");
        from("direct:convertPainToPacs").log("Input  Value for convertToPain api - "+"${body}").marshal().json(JsonLibrary.Jackson)
                .convertBodyTo(String.class)
                .process(conversionProcessor)
                .convertBodyTo(String.class)
                .process(exchange -> exchange.getIn().setBody(new XsdValidationModel(exchange.getIn().getHeader("pacsXsdPath", String.class),
                            null, exchange.getIn().getHeader("pacsXml", String.class))))
                .to("direct:placeTargetFile");
        from("direct:placeTargetFile")
                .process(moveFileProcessor)
                .end();

    }

}

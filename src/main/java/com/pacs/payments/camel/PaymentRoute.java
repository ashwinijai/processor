package com.pacs.payments.camel;

import com.pacs.payments.processor.DBProcessor;
import com.pacs.payments.processor.FetchFIleProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PaymentRoute extends RouteBuilder{
    public void configure() throws Exception {

        from("direct:fetchPathFromUrl")
                .log("Inside Get File Path route")
                .process(new DBProcessor())
                .log("SourceXsdPath - "+"${header.sourceXsdPath}")
                .log("PainXsdPath - "+"${header.painXsdPath}")
                .log("SourceXmlPath - "+"${header.sourceXmlPath}")
                .to("direct:getFilesFromLocation");
        from("direct:getFilesFromLocation")
                .log("Inside Get File from Location route")
                .process(new FetchFIleProcessor())
                .end();
                //.to("{{route.validateXsd}}");
    }

}

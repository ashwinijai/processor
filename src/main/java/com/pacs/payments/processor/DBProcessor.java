package com.pacs.payments.processor;

import com.pacs.payments.entity.PacsFileDetails;
import com.pacs.payments.model.CamelRequest;
import com.pacs.payments.repo.PacsFileDetailsRepository;
import com.pacs.payments.repo.PacsFileDetailsRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        CamelRequest camelRequest = exchange.getIn().getBody(CamelRequest.class);
        PacsFileDetails pacsFileDetails =  PacsFileDetails.builder().sourceXsdPath("/Users/ashwinijayaraman/Downloads/archive_payments_clearing_and_settlement_9_9b41eab473/pacs.002.001.10.xsd").painXsdPath("/Users/ashwinijayaraman/Downloads/archive_payments_initiation_3_0625d854ae/pain.002.001.03.xsd").sourceXmlPath("/Users/ashwinijayaraman/Downloads/sourceXml.xml").build();
        if(null!=pacsFileDetails){
            exchange.getIn().setHeader("sourceXsdPath", pacsFileDetails.getSourceXsdPath());
            exchange.getIn().setHeader("painXsdPath", pacsFileDetails.getPainXsdPath());
            exchange.getIn().setHeader("sourceXmlPath", pacsFileDetails.getSourceXmlPath());
        }
        else{
            throw new Exception("No value found in DB for given input combination");
        }
    }
}

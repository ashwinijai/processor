package com.pacs.payments.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class FetchFIleProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String sourceXsdPath = (String) exchange.getIn().getHeader("sourceXsdPath");
        String painXsdPath = (String) exchange.getIn().getHeader("painXsdPath");
        String sourceXmlPath = (String) exchange.getIn().getHeader("sourceXmlPath");
        exchange.getIn().setHeader("sourceXsd", getFileFromLocation(sourceXsdPath));
        exchange.getIn().setHeader("painXsd", getFileFromLocation(painXsdPath));
        exchange.getIn().setHeader("sourceXml", getFileFromLocation(sourceXmlPath));
    }

    private String getFileFromLocation(String path){
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}

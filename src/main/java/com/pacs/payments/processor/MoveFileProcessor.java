package com.pacs.payments.processor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pacs.payments.model.MoveFileModel;
import com.pacs.paymentsUtil.service.FileProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MoveFileProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        MoveFileModel moveFileModel = new MoveFileModel();
        moveFileModel.setFileContent(exchange.getIn().getHeader("pacsXml", String.class));
        moveFileModel.setFilePath(exchange.getIn().getHeader("targetPacsXmlPath", String.class));
        moveFileModel.setFileName(exchange.getIn().getHeader("paymentType", String.class)+".xml");
        FileProcessorService fileProcessorService = new FileProcessorService();
        String result = fileProcessorService.placeFileInLocation(moveFileModel.getFilePath(), moveFileModel.getFileContent(), moveFileModel.getFileName());
        log.info(result);
    }
}

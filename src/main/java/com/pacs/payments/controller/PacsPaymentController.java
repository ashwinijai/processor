package com.pacs.payments.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pacs.payments.model.FetchFilePathRequest;
import com.pacs.payments.model.LookupRequest;
import com.pacs.payments.service.ValueService;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacs")
public class PacsPaymentController {
    @Autowired
    ProducerTemplate producerTemplate;
    @Autowired
    ValueService lookupService;

    @PostMapping("/validateConvertSend")
    public String validateConvertSend(@RequestBody FetchFilePathRequest camelRequest){
        producerTemplate.sendBody("direct:fetchFileLocations", camelRequest);
        return "File Moved successfully";
    }

    @PostMapping("/getLookUpValue")
    public String getLookupValue(@RequestBody LookupRequest lookupRequest) throws JsonProcessingException {
        return lookupService.getLookUpValue(lookupRequest.getSource(), lookupRequest.getTarget());
    }

}

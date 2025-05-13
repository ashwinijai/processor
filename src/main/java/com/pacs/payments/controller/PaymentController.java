package com.pacs.payments.controller;

import com.pacs.payments.model.CamelRequest;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pacs")
public class PaymentController {
    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    CamelContext camelContext;

    @PostMapping("/validateConvertSend")
    public String validateConvertSend(@RequestBody CamelRequest camelRequest){
        producerTemplate.sendBody("direct:fetchPathFromUrl",camelRequest);
        return "File Moved successfully";
    }
}

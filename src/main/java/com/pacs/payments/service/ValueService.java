package com.pacs.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pacs.paymentsUtil.service.LookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ValueService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public String getLookUpValue(String source, String target) throws JsonProcessingException {
        LookupService service = new LookupService();
        return service.getLookUpValue(source,target,jdbcTemplate);
    }
}

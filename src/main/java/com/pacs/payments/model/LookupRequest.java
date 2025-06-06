package com.pacs.payments.model;

import lombok.Data;

@Data
public class LookupRequest {
    String source;
    String target;
}

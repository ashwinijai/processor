package com.pacs.payments.model;

import lombok.Data;

@Data
public class FetchFilePathResponse {
    private String pacsXsdPath;
    private String painXsdPath;
    private String painXmlPath;
    private String targetPacsXmlPath;
    private String paymentType;
    private String sourceSystem;
}

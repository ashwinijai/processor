package com.pacs.payments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CamelRequest {
    private String sourceSystem;
    private String paymentType;
}

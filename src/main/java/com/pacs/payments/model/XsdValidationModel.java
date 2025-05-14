package com.pacs.payments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class XsdValidationModel {
    private String xsdPath;
    private String xmlPath;
}

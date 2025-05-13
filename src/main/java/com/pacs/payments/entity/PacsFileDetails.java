package com.pacs.payments.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="PACS_FILE_DETAILS")
@Data
@Builder
public class PacsFileDetails {
    @Id
    @Column(name="SNO")
    private Long sNo;
    @Column(name="SOURCE_SYSTEM")
    private String sourceSystem;
    @Column(name="PAYMENT_TYPE")
    private String paymentType;
    @Column(name="SOURCE_XSD_PATH")
    private String sourceXsdPath;
    @Column(name="PAIN_XSD_PATH")
    private String painXsdPath;
    @Column(name="SOURCE_XML_PATH")
    private String sourceXmlPath;
    @Column(name="TARGET_XML_PATH")
    private String targetXmlPath;
}

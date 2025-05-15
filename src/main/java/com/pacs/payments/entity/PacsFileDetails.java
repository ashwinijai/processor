package com.pacs.payments.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="PACS_FILE_DETAILS")
@Data
@NoArgsConstructor
public class PacsFileDetails {
    @Id
    @Column(name="SNO")
    private Long sNo;
    @Column(name="SOURCE_SYSTEM")
    private String sourceSystem;
    @Column(name="PAYMENT_TYPE")
    private String paymentType;
    @Column(name="PACS_XSD_PATH")
    private String pacsXsdPath;
    @Column(name="PAIN_XSD_PATH")
    private String painXsdPath;
    @Column(name="PAIN_XML_PATH")
    private String painXmlPath;
    @Column(name="TARGET_PACS_XML_PATH")
    private String targetPacsXmlPath;
}

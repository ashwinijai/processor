package com.pacs.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PacsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PacsApplication.class, args);
	}
	//create table T_GAMS_IACH_AUDIT_FILE_INFO(DEST_CCY VARCHAR(20), CR_DR_IND VARCHAR(20), FILE_ID NUMBER);
	//INSERT INTO T_GAMS_IACH_AUDIT_FILE_INFO(DEST_CCY, CR_DR_IND, FILE_ID) VALUES('CAD','CT',31);

}


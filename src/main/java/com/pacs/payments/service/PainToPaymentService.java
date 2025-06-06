package com.pacs.payments.service;


import com.pacs.paymentsUtil.pacs.processor.FIToFIPaymentStatusReportV10;
import com.pacs.paymentsUtil.pacs.processor.GroupHeader91;
import com.pacs.paymentsUtil.pacs.processor.OriginalGroupHeader17;
import com.pacs.paymentsUtil.pain.processor.Document;
import com.pacs.paymentsUtil.pain.processor.OriginalGroupInformation20;
import com.pacs.paymentsUtil.utility.CommonUtility;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;


public class PainToPaymentService {
    public String convertToPain(String paymentType, String painPath) {
        if (paymentType.equals("PACS002")) {
            try {
                return convertToPain(CommonUtility.getFileFromLocation(painPath));
            } catch (JAXBException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    private String convertToPain(String painMessage) throws JAXBException {
        String pacsXml = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement<Document> jaxbElement = (JAXBElement<Document>) jaxbUnmarshaller
                .unmarshal(new StreamSource(new StringReader(painMessage)), Document.class);
        Document painDocument =jaxbElement.getValue();
        if (null != painDocument && null != painDocument.getCstmrPmtStsRpt()) {
            com.pacs.paymentsUtil.pacs.processor.Document pacsDocument = new com.pacs.paymentsUtil.pacs.processor.Document();
            FIToFIPaymentStatusReportV10 fIToFIPaymentStatusReportV10 = new FIToFIPaymentStatusReportV10();
            pacsDocument.setFIToFIPmtStsRpt(fIToFIPaymentStatusReportV10);
            if (null != painDocument.getCstmrPmtStsRpt().getGrpHdr()) {
                GroupHeader91 groupHeader = new GroupHeader91();
                groupHeader.setMsgId(painDocument.getCstmrPmtStsRpt().getGrpHdr().getMsgId());
                groupHeader.setCreDtTm(painDocument.getCstmrPmtStsRpt().getGrpHdr().getCreDtTm());
                fIToFIPaymentStatusReportV10.setGrpHdr(groupHeader);
            }
            if (null != painDocument.getCstmrPmtStsRpt().getOrgnlGrpInfAndSts()) {
                OriginalGroupInformation20 painOrgGroupInfo = painDocument.getCstmrPmtStsRpt().getOrgnlGrpInfAndSts();
                OriginalGroupHeader17 originalGroupInfo = new OriginalGroupHeader17();
                originalGroupInfo.setOrgnlMsgId(painOrgGroupInfo.getOrgnlMsgId());
                originalGroupInfo.setOrgnlMsgNmId(painOrgGroupInfo.getOrgnlMsgNmId());
                originalGroupInfo.setOrgnlNbOfTxs(painOrgGroupInfo.getOrgnlNbOfTxs());
                originalGroupInfo.setOrgnlCtrlSum(painOrgGroupInfo.getOrgnlCtrlSum());
                originalGroupInfo.setGrpSts(painOrgGroupInfo.getGrpSts().value());
                fIToFIPaymentStatusReportV10.getOrgnlGrpInfAndSts().add(originalGroupInfo);
            }
            pacsXml = convertToString(pacsDocument);
            System.out.println("Converted PACS xml - "+ pacsXml);
        }
        return pacsXml;
    }

    public static String convertToString(Object jaxbObject) throws JAXBException {
        JAXBElement<?> jaxbElement = null;
        JAXBContext jaxbContext = JAXBContext.newInstance(jaxbObject.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        if(jaxbObject instanceof com.pacs.paymentsUtil.pacs.processor.Document pacsDocument) {
            jaxbElement
                    = new JAXBElement<>(new QName("", "document"), com.pacs.paymentsUtil.pacs.processor.Document.class, pacsDocument);
        }
        if(null!=jaxbElement) {
            StringWriter sw = new StringWriter();
            jaxbMarshaller.marshal(jaxbElement, sw);
            return sw.toString();
        }
        return null;
    }

}


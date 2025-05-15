package com.pacs.payments.service;

import com.pacs.payments.entity.PacsFileDetails;
import com.pacs.payments.model.FetchFilePathResponse;
import com.pacs.payments.repo.PacsFileDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    PacsFileDetailsRepository pacsFileDetailsRepository;

    public FetchFilePathResponse getFilePath(String sourceSystem, String paymentType){
        FetchFilePathResponse fetchFilePathResponse=null;
        PacsFileDetails pacsFileDetails = pacsFileDetailsRepository.getBySourceSystemAndPaymentType(sourceSystem, paymentType);
        if(null!=pacsFileDetails){
            fetchFilePathResponse = new FetchFilePathResponse();
            fetchFilePathResponse.setPacsXsdPath(pacsFileDetails.getPacsXsdPath());
            fetchFilePathResponse.setPainXsdPath(pacsFileDetails.getPainXsdPath());
            fetchFilePathResponse.setPainXmlPath(pacsFileDetails.getPainXmlPath());
            fetchFilePathResponse.setTargetPacsXmlPath(pacsFileDetails.getTargetPacsXmlPath());
            fetchFilePathResponse.setPaymentType(pacsFileDetails.getPaymentType());
            fetchFilePathResponse.setSourceSystem(pacsFileDetails.getSourceSystem());
        }
        return fetchFilePathResponse;
    }
}

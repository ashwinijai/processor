package com.pacs.payments.repo;

import com.pacs.payments.entity.PacsFileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacsFileDetailsRepository extends JpaRepository<PacsFileDetails, Long> {

    @Query("select p from PacsFileDetails p where p.sourceSystem =:sourceSystem and p.paymentType=:paymentType")
    PacsFileDetails getBySourceSystemAndPaymentType(@Param("sourceSystem")String sourceSystem, @Param("paymentType") String paymentType);
}

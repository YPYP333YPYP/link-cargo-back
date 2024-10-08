package com.example.linkcargo.domain.quotation;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuotationRepository extends MongoRepository<Quotation, String> {

    boolean existsByConsignorIdAndFreight_ScheduleId(String consignorId, String scheduleId);

    List<Quotation> findQuotationsByConsignorId(String s);

    Optional<Quotation> findQuotationByQuotationStatusAndFreight_scheduleIdAndConsignorId(QuotationStatus quotationStatus, String consignorId, String scheduleId);

    List<Quotation> findQuotationsByIdOrOriginalQuotationId(String quotationId, String originalQuotationId);

    List<Quotation> findQuotationsByOriginalQuotationIdAndQuotationStatus(String originalQuotationId, QuotationStatus quotationStatus);

    Optional<Quotation> findQuotationByOriginalQuotationIdAndQuotationStatus(String originalQuotationId, QuotationStatus quotationStatus);
}

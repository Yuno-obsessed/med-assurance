package sanity.nil.medassurance.dto.request;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateRefundDTO(
        BigDecimal amount, String refundStatus,
        UUID assuranceID, UUID diagnosisID
) {}

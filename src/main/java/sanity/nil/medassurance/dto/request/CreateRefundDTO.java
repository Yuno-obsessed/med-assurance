package sanity.nil.medassurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateRefundDTO(
        @JsonProperty(value = "amount")
        BigDecimal amount,
        @JsonProperty(value = "assurance_id")
        UUID assuranceID,
        @JsonProperty(value = "diagnosis_id")
        UUID diagnosisID,
        @JsonProperty(value = "operation_id")
        Integer operationID,
        @JsonProperty(value = "doctor_id")
        Integer doctorID
) {}

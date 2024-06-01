package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RefundCardDTO(
        @JsonProperty(value = "amount")
        BigDecimal amount,
        @JsonProperty(value = "refund_status")
        String refundStatus,
        @JsonProperty(value = "assurance_type")
        String assuranceType,
        @JsonProperty(value = "diagnosis_name")
        String diagnosisName,
        @JsonProperty(value = "operation_name")
        String operationName,
        @JsonProperty(value = "doctor_name")
        String doctorName,
        @JsonProperty(value = "updated_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime updatedAt
) {}

package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AssuranceCardDTO(
        @JsonProperty(value = "assurance_type")
        String assuranceType,
        @JsonProperty(value = "duration_type")
        String durationType,
        @JsonProperty(value = "percent")
        BigDecimal percent,
        @JsonProperty(value = "active_until")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime activeUntil
) {}

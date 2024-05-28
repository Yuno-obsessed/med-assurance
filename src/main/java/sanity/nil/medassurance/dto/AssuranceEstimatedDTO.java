package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AssuranceEstimatedDTO(
        @JsonProperty(value = "price")
        BigDecimal price,
        @JsonProperty(value = "active_until")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime activeUntil
) {}

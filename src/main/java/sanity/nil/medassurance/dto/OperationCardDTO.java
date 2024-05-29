package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OperationCardDTO(
        @JsonProperty(value = "operation_name")
        String operationName,
        @JsonProperty(value = "structure_name")
        String structureName,
        @JsonProperty(value = "structure_id")
        Integer structureID,
        @JsonProperty(value = "doctor_name")
        String doctorName,
        @JsonProperty(value = "doctor_id")
        Integer doctorID,
        @JsonProperty(value = "price")
        BigDecimal price
//        @JsonProperty(value = "soonest_availability")
//        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
//        LocalDateTime soonestAvailability
) {}

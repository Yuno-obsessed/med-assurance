package sanity.nil.medassurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OperationFilterDTO(
        @JsonProperty(value = "doctor_name")
        String doctorName,
        @JsonProperty(value = "structure_name")
        String structureName,
        @JsonProperty(value = "operation_name")
        String operationName
) {}

package sanity.nil.medassurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

public record OperationFilterDTO(
        @JsonProperty(value = "doctor_name")
        String doctorName,
        @JsonProperty(value = "structure_name")
        String structureName,
        @JsonProperty(value = "operation_name")
        String operationName,
        @JsonProperty(value = "offset")
        Integer offset,
        @JsonProperty(value = "limit")
        Integer limit
) {}

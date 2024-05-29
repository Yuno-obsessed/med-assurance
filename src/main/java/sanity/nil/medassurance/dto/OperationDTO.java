package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OperationDTO(
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "description")
        String description
) {}

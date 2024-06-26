package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StructureDTO(
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "address")
        String address
) {}

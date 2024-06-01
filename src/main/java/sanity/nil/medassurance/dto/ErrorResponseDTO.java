package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponseDTO(
        @JsonProperty(value = "message")
        String message
) {}

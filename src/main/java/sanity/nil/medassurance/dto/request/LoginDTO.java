package sanity.nil.medassurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginDTO(
        @JsonProperty(value = "email", required = true)
        String email,
        @JsonProperty(value = "password", required = true)
        String password
) {}

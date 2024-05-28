package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import sanity.nil.medassurance.consts.AuthError;

public record LoginResponseDTO (
        @JsonProperty(value = "access_token")
        String accessToken,
        @JsonProperty(value = "error")
        AuthError error
) {}

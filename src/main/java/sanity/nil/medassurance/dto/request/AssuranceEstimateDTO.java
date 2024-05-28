package sanity.nil.medassurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AssuranceEstimateDTO(
        @JsonProperty(value = "assurance_type")
        String assuranceType,
        @JsonProperty(value = "duration_type")
        String durationType
) {}

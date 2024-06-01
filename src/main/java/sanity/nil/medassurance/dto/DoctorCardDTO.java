package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DoctorCardDTO(
        @JsonProperty(value = "id")
        Integer id,
        @JsonProperty(value = "name")
        String name
) {}

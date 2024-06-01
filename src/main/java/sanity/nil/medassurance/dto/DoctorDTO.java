package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DoctorDTO(
        @JsonProperty(value = "name")
        String name,
        @JsonProperty(value = "specialty")
        String specialty,
        @JsonProperty(value = "operations")
        List<DoctorOperationDTO> operationDTO
) {}

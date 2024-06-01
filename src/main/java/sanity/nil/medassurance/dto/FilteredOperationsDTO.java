package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FilteredOperationsDTO(
        @JsonProperty(value = "operations")
        List<OperationCardDTO> operations,
        @JsonProperty(value = "total_pages")
        int totalPages,
        @JsonProperty(value = "current_page")
        int currentPage
) {}

package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FilteredRefundsDTO(
        @JsonProperty(value = "refunds")
        List<RefundCardDTO> refunds,
        @JsonProperty(value = "total_pages")
        int totalPages,
        @JsonProperty(value = "current_page")
        int currentPage
) {}

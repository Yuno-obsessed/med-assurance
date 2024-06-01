package sanity.nil.medassurance.dto;

import java.util.UUID;

public record RefundFilterDTO(
        UUID userID,
        Integer offset,
        Integer limit
) {}

package sanity.nil.medassurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserDTO(
        @JsonProperty(value = "first_name")
        String firstName,
        @JsonProperty(value = "last_name")
        String lastName,
        @JsonProperty(value = "age")
        String age,
        @JsonProperty(value = "passport_number")
        String passportNumber,
        @JsonProperty(value = "email")
        String email,
        @JsonProperty(value = "address")
        String address,
        @JsonProperty(value = "children")
        int children,
        @JsonProperty(value = "iban")
        String iban,
        @JsonProperty(value = "created_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime createdAt
) {}

package sanity.nil.medassurance.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateUserDTO(
        @JsonProperty(value = "first_name")
        String firstName,
        @JsonProperty(value = "last_name")
        String lastName,
        @JsonProperty(value = "age")
        String age,
        @JsonProperty(value = "passport_number")
        String passportNumber,
        @JsonProperty(value = "password")
        String password,
        @JsonProperty(value = "email")
        String email,
        @JsonProperty(value = "address")
        String address,
        @JsonProperty(value = "income_type")
        String incomeType,
        @JsonProperty(value = "children")
        int children,
        @JsonProperty(value = "iban")
        String iban
) {}

package sanity.nil.medassurance.dto.request;

public record CreateUserDTO(
        String firstName, String lastName, String age,
        String passportNumber, String email, String address,
        String incomeType, int children, String iban
) {}

package cz.soukup.lunde.ticket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class CreateTicketRequest {

    @NotNull
    private final Long ticketTypeId;

    @NotBlank(message = "Policy number must not be blank.")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Invalid policy number. Only alphanumeric chars are allowed.")
    private final String policyNumber;


    @NotBlank(message = "Name must not be blank.")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Invalid name. Only alphabetic chars are allowed.")
    private final String name;

    @NotBlank(message = "Surname must not be blank.")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Invalid policy number. Only alphabetic chars are allowed.")
    private final String surname;

    @NotBlank(message = "Customer request must not be blank.")
    @Length(min = 3, max = 5000)
    private final String customerRequest;


}

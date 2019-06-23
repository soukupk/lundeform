package cz.soukup.lunde.controllers.forms;

import cz.soukup.lunde.ticket.CreateTicketRequest;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketForm {

    private Long ticketTypeId;

    private String policyNumber;

    private String name;

    private String surname;

    private String customerRequest;

    public CreateTicketRequest getCreateTicketRequest() {
        return CreateTicketRequest.create(ticketTypeId, policyNumber, name, surname, customerRequest);
    }

}

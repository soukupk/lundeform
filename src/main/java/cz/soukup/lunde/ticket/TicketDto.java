package cz.soukup.lunde.ticket;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class TicketDto {

    private final Long id;

    private final String ticketTypeName;

    private final String policyNumber;

    private final String name;

    private final String surname;

    private final String customerRequest;

    static TicketDto create(Ticket ticket) {
        return new TicketDto(ticket.getId(), ticket.getTicketType().getName(), ticket.getPolicyNumber(),
                ticket.getName(), ticket.getSurname(), ticket.getCustomerRequest());
    }
}

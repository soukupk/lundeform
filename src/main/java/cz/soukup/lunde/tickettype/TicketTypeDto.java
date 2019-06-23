package cz.soukup.lunde.tickettype;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class TicketTypeDto {

    private final Long id;

    private final String name;

    private TicketTypeDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    static final TicketTypeDto create(TicketType ticketType) {
        return new TicketTypeDto(ticketType.getId(), ticketType.getName());
    }

    public static final TicketTypeDto create(Long id, String name) {
        return new TicketTypeDto(id, name);
    }

}

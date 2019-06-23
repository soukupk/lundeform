package cz.soukup.lunde.tickettype;

import java.util.List;
import java.util.Optional;

public interface TicketTypeRepository {

    TicketType save(TicketType ticketType);

    Optional<TicketType> findById(Long id);

    List<TicketType> findAll();

}

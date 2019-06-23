package cz.soukup.lunde.repositories;

import cz.soukup.lunde.ticket.Ticket;
import cz.soukup.lunde.ticket.TicketRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketJpaRepository extends CrudRepository<Ticket, Long>, TicketRepository {

}

package cz.soukup.lunde.repositories;

import cz.soukup.lunde.tickettype.TicketType;
import cz.soukup.lunde.tickettype.TicketTypeRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeJpaRepository extends CrudRepository<TicketType, Long>, TicketTypeRepository {

}

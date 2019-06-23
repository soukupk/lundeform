package cz.soukup.lunde.ticket;

import com.google.common.collect.ImmutableSet;
import cz.soukup.lunde.common.ServiceResponse;
import cz.soukup.lunde.common.ValidationError;
import cz.soukup.lunde.tickettype.TicketType;
import cz.soukup.lunde.tickettype.TicketTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class TicketCreator {

    private final TicketTypeRepository ticketTypeRepository;

    private final TicketRepository ticketRepository;

    private final Validator validator;

    @Autowired
    public TicketCreator(TicketTypeRepository ticketTypeRepository, TicketRepository ticketRepository, Validator validator) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.ticketRepository = ticketRepository;
        this.validator = validator;
    }

    @Transactional
    public ServiceResponse<TicketDto> create(CreateTicketRequest createTicketRequest) {
        Set<ConstraintViolation<CreateTicketRequest>> violationSet = validator.validate(createTicketRequest);
        if (!violationSet.isEmpty()) {
            return ServiceResponse.createFailureResponse(ValidationError.createSet(violationSet));
        }

        Optional<TicketType> ticketTypeOptional = ticketTypeRepository.findById(createTicketRequest.getTicketTypeId());
        if (! ticketTypeOptional.isPresent()) {
            return ServiceResponse.createFailureResponse(ImmutableSet.of(
                    ValidationError.create("ticketTypeId", "Ticket type not found.")));
        }

        Ticket ticket = createTicket(createTicketRequest, ticketTypeOptional.get());
        ticketRepository.save(ticket);
        log.info("New ticket created id:{}", ticket.getId());
        return ServiceResponse.createSuccessResponse(TicketDto.create(ticket));
    }

    private Ticket createTicket(CreateTicketRequest createTicketRequest, TicketType ticketType) {
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setPolicyNumber(createTicketRequest.getPolicyNumber());
        ticket.setName(createTicketRequest.getName());
        ticket.setSurname(createTicketRequest.getSurname());
        ticket.setCustomerRequest(createTicketRequest.getCustomerRequest());
        return ticket;
    }

}

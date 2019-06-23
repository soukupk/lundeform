package cz.soukup.lunde.tickettype;

import com.google.common.collect.ImmutableSet;
import cz.soukup.lunde.common.ServiceResponse;
import cz.soukup.lunde.common.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;

    @Autowired
    public TicketTypeService(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<TicketTypeDto> getAll() {
        return ticketTypeRepository.findAll().stream()
                .map(TicketTypeDto::create)
                .collect(Collectors.toList());
    }

    @Transactional
    public ServiceResponse<TicketTypeDto> create(String name) {
        TicketType ticketType = new TicketType();

        if (StringUtils.isEmpty(name)) {
            return ServiceResponse.createFailureResponse(ImmutableSet.of(
                    ValidationError.create("name", "Name is empty.")));
        }

        ticketType.setName(name);
        ticketTypeRepository.save(ticketType);

        log.info("New ticketType created: {}", ticketType.getId());
        return ServiceResponse.createSuccessResponse(TicketTypeDto.create(ticketType));
    }

}

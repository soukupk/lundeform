package cz.soukup.lunde.ticket;

import com.google.common.collect.ImmutableMap;
import cz.soukup.lunde.common.ServiceResponse;
import cz.soukup.lunde.common.ValidationError;
import cz.soukup.lunde.tickettype.TicketType;
import cz.soukup.lunde.tickettype.TicketTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TicketCreatorTest {

    @Mock
    TicketTypeRepository ticketTypeRepository;

    @Mock
    TicketRepository ticketRepository;

    @Spy
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @InjectMocks
    TicketCreator ticketCreator;

    @Before
    public void setUp() {
        Mockito.when(ticketRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArgument(0, Ticket.class));
    }

    @Test
    public void create() {
        TicketType ticketType = TicketType.builder().id(10l).name("Contract Adjustment").build();
        Mockito.when(ticketTypeRepository.findById(10l))
                .thenReturn(Optional.of(ticketType));

        ServiceResponse<TicketDto> ticketDtoServiceResponse = ticketCreator.create(CreateTicketRequest
                .create(10l, "ABC1923", "Jan", "Doe",
                        "Could you please help me?"));

        TicketDto expectedTicketDto = new TicketDto(null, "Contract Adjustment", "ABC1923",
                "Jan", "Doe", "Could you please help me?");
        assertEquals(ServiceResponse.createSuccessResponse(expectedTicketDto), ticketDtoServiceResponse);

        Mockito.verify(ticketRepository).save(Ticket.builder()
                .ticketType(ticketType)
                .policyNumber("ABC1923")
                .name("Jan")
                .surname("Doe")
                .customerRequest("Could you please help me?")
                .build());
    }

    @Test
    public void createFailsIfTicketTypeNotExists() {
        Mockito.when(ticketTypeRepository.findById(10l))
                .thenReturn(Optional.empty());

        ServiceResponse<TicketDto> ticketDtoServiceResponse = ticketCreator.create(CreateTicketRequest
                .create(10l, "ABC1923", "Jan", "Doe",
                        "Could you please help me?"));

        assertEquals(ServiceResponse.createFailureResponse(ValidationError.createSet(ImmutableMap.of(
                "ticketTypeId", "Ticket type not found."
        ))), ticketDtoServiceResponse);
    }

    @Test
    public void createFailsIfInvalidInput() {
        ServiceResponse<TicketDto> ticketDtoServiceResponse = ticketCreator.create(CreateTicketRequest
                .create(10l, "ABC1923$$$$$$INVALID#", "Jan102invalid", "Doe103invalid",
                        "Could you please help me?"));

        assertEquals(ServiceResponse.createFailureResponse(ValidationError.createSet(ImmutableMap.of(
                "policyNumber", "Invalid policy number. Only alphanumeric chars are allowed.",
                "name", "Invalid name. Only alphabetic chars are allowed.",
                "surname", "Invalid policy number. Only alphabetic chars are allowed."
        ))), ticketDtoServiceResponse);
    }
}
package cz.soukup.lunde.tickettype;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TicketTypeServiceTest {

    @Mock
    private TicketTypeRepository ticketTypeRepository;

    @InjectMocks
    private TicketTypeService ticketTypeService;

    @Test
    public void getAll() {
        Mockito.when(ticketTypeRepository.findAll())
                .thenReturn(Collections.singletonList(
                        TicketType.builder().id(10l).name("Contract Adjustment").build()));
        List<TicketTypeDto> ticketTypeDtoList = ticketTypeService.getAll();

        List<TicketTypeDto> expectedTicketTypeDtoList = Collections.singletonList(
                TicketTypeDto.create(10l, "Contract Adjustment"));
        assertEquals(expectedTicketTypeDtoList, ticketTypeDtoList);
    }

    @Test
    public void create() {
        Mockito.when(ticketTypeRepository.save(Mockito.any()))
                .thenAnswer(i -> i.getArgument(0, TicketType.class));

        TicketTypeDto ticketTypeDto = ticketTypeService.create("Contract Adjustment").getModel();

        TicketTypeDto expectedTicketTypeDtoList = TicketTypeDto.create(null, "Contract Adjustment");
        assertEquals(expectedTicketTypeDtoList, ticketTypeDto);
    }
}
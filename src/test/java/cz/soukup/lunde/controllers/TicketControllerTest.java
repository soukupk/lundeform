package cz.soukup.lunde.controllers;

import com.google.common.collect.ImmutableMap;
import cz.soukup.lunde.LundeApplication;
import cz.soukup.lunde.common.FormErrors;
import cz.soukup.lunde.controllers.forms.CreateTicketForm;
import cz.soukup.lunde.tickettype.TicketTypeDto;
import cz.soukup.lunde.tickettype.TicketTypeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LundeApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    TicketTypeService ticketTypeService;

    @Test
    public void createTicket() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ticketTypes"))
                .andExpect(model().attribute("createTicketForm", new CreateTicketForm()))
                .andExpect(model().attribute("formErrors", FormErrors.createEmpty()));
    }

    @Test
    public void createTicketSubmit() throws Exception {
        TicketTypeDto ticketTypeDto = ticketTypeService.create("Contract Adjustment").getModel();
        mvc.perform(post("/")
                .param("ticketTypeId", ticketTypeDto.getId().toString())
                .param("policyNumber", "100012")
                .param("name", "Jan")
                .param("surname", "Doe")
                .param("customerRequest", "Could you please help me."))
                .andExpect(status().is3xxRedirection())
                .andExpect(flash().attribute("flashSuccessMessage", "New ticket created. Thank you."));
    }

    @Test
    public void createTicketSubmitReturnsErrorsWhenInvalidInput() throws Exception {
        TicketTypeDto ticketTypeDto = ticketTypeService.create("Contract Adjustment").getModel();
        mvc.perform(post("/")
                .param("ticketTypeId", ticketTypeDto.getId().toString())
                .param("policyNumber", "100012&&&INVALID")
                .param("name", "Jan")
                .param("surname", "Doe")
                .param("customerRequest", "Could you please help me."))
                .andExpect(status().isOk())
                .andExpect(model().attribute("ticketTypes", Collections.singletonList(ticketTypeDto)))
                .andExpect(model().attribute("createTicketForm", CreateTicketForm.builder()
                        .ticketTypeId(ticketTypeDto.getId())
                        .policyNumber("100012&&&INVALID")
                        .name("Jan")
                        .surname("Doe")
                        .customerRequest("Could you please help me.")
                        .build()))
                .andExpect(model().attribute("formErrors", FormErrors.create(
                        ImmutableMap.of("policyNumber", Collections.singletonList(
                                "Invalid policy number. Only alphanumeric chars are allowed.")))));
    }
}
package cz.soukup.lunde.controllers;

import com.google.common.collect.ImmutableMap;
import cz.soukup.lunde.common.FormErrors;
import cz.soukup.lunde.common.ServiceResponse;
import cz.soukup.lunde.controllers.forms.CreateTicketForm;
import cz.soukup.lunde.ticket.TicketCreator;
import cz.soukup.lunde.ticket.TicketDto;
import cz.soukup.lunde.tickettype.TicketTypeDto;
import cz.soukup.lunde.tickettype.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TicketController {

    private final TicketTypeService ticketTypeService;

    private final TicketCreator ticketCreator;

    @Autowired
    public TicketController(TicketTypeService ticketTypeService, TicketCreator ticketCreator) {
        this.ticketTypeService = ticketTypeService;
        this.ticketCreator = ticketCreator;
    }

    @GetMapping("/")
    public ModelAndView createTicket() {
        createDemoTicketTypesIfNeeded();

        List<TicketTypeDto> ticketTypeDtoList = ticketTypeService.getAll();
        return new ModelAndView("createTicket", ImmutableMap.of(
                "ticketTypes", ticketTypeDtoList,
                "formErrors", FormErrors.createEmpty(),
                "createTicketForm", new CreateTicketForm()
        ));
    }

    @PostMapping("/")
    public ModelAndView createTicketSubmit(CreateTicketForm createTicketForm, RedirectAttributes redirectAttributes) {
        ServiceResponse<TicketDto> createTicketResponse = ticketCreator.create(createTicketForm.getCreateTicketRequest());

        if (createTicketResponse.isSuccess()) {
            redirectAttributes.addFlashAttribute("flashSuccessMessage",
                    "New ticket created. Thank you.");
            return new ModelAndView("redirect:/");
        }

        List<TicketTypeDto> ticketTypeDtoList = ticketTypeService.getAll();
        return new ModelAndView("createTicket", ImmutableMap.of(
                "ticketTypes", ticketTypeDtoList,
                "formErrors", FormErrors.create(createTicketResponse.getValidationErrors()),
                "createTicketForm", createTicketForm
        ));
    }

    private void createDemoTicketTypesIfNeeded() {
        List<TicketTypeDto> ticketTypeDtoList = ticketTypeService.getAll();

        if (ticketTypeDtoList.isEmpty()) {
            ticketTypeService.create("Contract Adjustment");
            ticketTypeService.create("Damage Case");
            ticketTypeService.create("Complaint");
        }
    }

}

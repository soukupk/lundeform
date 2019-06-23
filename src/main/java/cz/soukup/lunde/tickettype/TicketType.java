package cz.soukup.lunde.tickettype;

import cz.soukup.lunde.ticket.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "request_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketType {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "ticketType", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

}

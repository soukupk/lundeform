package cz.soukup.lunde.ticket;

import cz.soukup.lunde.tickettype.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private TicketType ticketType;

    private String policyNumber;

    private String name;

    private String surname;

    @Column(columnDefinition = "text")
    private String customerRequest;

}

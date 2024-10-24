package com.rental.ezcars.service;

import com.rental.ezcars.entity.CustomerSupport;
import java.util.List;
import java.util.Optional;

public interface CustomerSupportService {
    List<CustomerSupport> getAllTickets();
    List<CustomerSupport> getAllTicketsByCustomerId(Long customerId);
    Optional<CustomerSupport> getTicketById(Long ticketId);
    CustomerSupport createTicket(CustomerSupport customerSupport);
    CustomerSupport updateTicket(Long ticketId, CustomerSupport updatedTicket);
    void deleteTicket(Long ticketId);
}
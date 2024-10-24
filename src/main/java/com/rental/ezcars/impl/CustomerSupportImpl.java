package com.rental.ezcars.impl;

import com.rental.ezcars.entity.CustomerSupport;
import com.rental.ezcars.repository.CustomerSupportRepository;
import com.rental.ezcars.service.CustomerSupportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerSupportImpl implements CustomerSupportService {

    @Autowired
    private CustomerSupportRepository customerSupportRepository;

    @Override
    public List<CustomerSupport> getAllTickets() {
        return customerSupportRepository.findAll();
    }

    @Override
    public Optional<CustomerSupport> getTicketById(Long ticketId) {
        return customerSupportRepository.findById(ticketId);
    }

    @Override
    public CustomerSupport createTicket(CustomerSupport customerSupport) {
        customerSupport.setCreatedDate(LocalDateTime.now());
        customerSupport.setUpdatedDate(LocalDateTime.now());
        customerSupport.setStatus(CustomerSupport.TicketStatus.OPEN);
        return customerSupportRepository.save(customerSupport);
    }

    @Override
    public CustomerSupport updateTicket(Long ticketId, CustomerSupport updatedTicket) {
        Optional<CustomerSupport> existingTicket = customerSupportRepository.findById(ticketId);
        if (existingTicket.isPresent()) {
            CustomerSupport ticket = existingTicket.get();
            ticket.setStatus(updatedTicket.getStatus());
            ticket.setDescription(updatedTicket.getDescription());
            ticket.setCustomerName(updatedTicket.getCustomerName());
            ticket.setCustomerEmail(updatedTicket.getCustomerEmail());
            ticket.setPriority(updatedTicket.getPriority());
            ticket.setResolution(updatedTicket.getResolution());
            ticket.setUpdatedDate(LocalDateTime.now());
            return customerSupportRepository.save(ticket);
        }
        return null;
    }

    @Override
    public void deleteTicket(Long ticketId) {
        customerSupportRepository.deleteById(ticketId);
    }

	@Override
	public List<CustomerSupport> getAllTicketsByCustomerId(Long customerId) {
		 return customerSupportRepository.findByCustomerId(customerId);
	}
}
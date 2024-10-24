package com.rental.ezcars.controller;

import com.rental.ezcars.entity.CustomerSupport;
import com.rental.ezcars.service.CustomerSupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer-support")
public class CustomerSupportController {

    @Autowired
    private CustomerSupportService customerSupportService;

    @GetMapping
    public ResponseEntity<List<CustomerSupport>> getAllTickets() {
        List<CustomerSupport> tickets = customerSupportService.getAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerSupport>> getAllTicketsByCustomerId(@PathVariable Long customerId) {
        List<CustomerSupport> tickets = customerSupportService.getAllTicketsByCustomerId(customerId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<CustomerSupport> getTicketById(@PathVariable Long ticketId) {
        Optional<CustomerSupport> ticket = customerSupportService.getTicketById(ticketId);
        return ticket.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CustomerSupport> createTicket(@RequestBody CustomerSupport customerSupport) {
        CustomerSupport createdTicket = customerSupportService.createTicket(customerSupport);
        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<CustomerSupport> updateTicket(@PathVariable Long ticketId, @RequestBody CustomerSupport customerSupport) {
        CustomerSupport updatedTicket = customerSupportService.updateTicket(ticketId, customerSupport);
        if (updatedTicket != null) {
            return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        customerSupportService.deleteTicket(ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
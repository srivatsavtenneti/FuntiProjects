package com.tenneti.thousie.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenneti.thousie.model.Player;
import com.tenneti.thousie.service.TicketService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TicketController {
	
	@Autowired
	private TicketService ticketService;	
	
	@PostMapping(path = "/generateTickets")
	public void generateHousieTickets(@RequestBody List<Player> players) throws MessagingException, IOException {
		 ticketService.generateTickets(players);
	}
	
	@GetMapping(path = "/clearCache")
	public void clearCache() {
		ticketService.clearCache();
	}

}
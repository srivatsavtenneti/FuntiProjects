package com.tenneti.thousie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tenneti.thousie.model.Player;
import com.tenneti.thousie.service.TicketService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TicketController {
	
	@Autowired
	private TicketService ticketService;	
	
	@PostMapping(path = "/generateTickets")
	public void generateHousieTickets(@RequestBody List<Player> players) {
		 ticketService.generateTickets(players);
	}
	
	@GetMapping(path = "/clearCache")
	public void clearCache() {
		ticketService.clearCache();
	}

}
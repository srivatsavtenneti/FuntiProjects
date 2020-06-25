package com.tenneti.thousie.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TicketUniqueGenerator {

	public List<int[][]> modifyTickets(List<int[][]> tickets) {
		List<Integer> colList = new ArrayList<Integer>();
		for(int i=0; i < 9; i++) {
			colList = getColList(i);
			for(int j=0; j < tickets.size() ; j++) {
				for(int k=0; k < 3; k++) {
					if(tickets.get(j)[i][k] != 0) {
						tickets.get(j)[i][k] = getNumber(tickets.get(j)[i][k], colList);
					}				
				}
			}
		}
	    verifyTickets(tickets);
		return tickets;
	}
	
	
	private void verifyTickets(List<int[][]> tickets) {
		for(int i=0; i<tickets.size(); i++) {
			for(int j=0; j<9; j++) {
				sortTickets(tickets.get(i)[j]);
			}
		}
	}
	
	private void sortTickets(int[] tickets) {
		List<Integer> pos = new ArrayList<Integer>();
		for(int i=0; i< tickets.length; i++) {
			if(tickets[i] != 0) {
				pos.add(i);
			}
		}	
		
		switch(pos.size()) {
			case 2:
				swapNumbers(tickets, pos.get(0), pos.get(1));
				break;
			case 3:
				Arrays.sort(tickets);
				break;
		}
	}
	
	private void swapNumbers(int[] tickets, int pos1, int pos2) {
		int temp = 0;
		if(tickets[pos1] > tickets[pos2]) {
			temp = tickets[pos1];
			tickets[pos1] = tickets[pos2];
			tickets[pos2] = temp;
		}
	}
	
	private int getNumber(int number, List<Integer> colList) {
		int finalNumber = number;
		if(colList.contains(number)) {
			colList.remove(new Integer(number));
		} else {
			Random random = new Random();
			finalNumber = colList.get(random.nextInt(colList.size()));
			colList.remove(new Integer(finalNumber));
		}

		return finalNumber;
	}	
	
	private List<Integer> getColList(int col) {
		List<Integer> colList = new ArrayList<Integer>();
		int colRange = 1;
		int i = 0;
		switch(col) {
			case 0: 
				colRange = 10;
				i = 1;
				break;
			case 8:
				colRange = 11;
				i = 0;
				break;
			default :
				colRange = 10;
				i = 0;
				break;
		}
		
		for(; i < colRange; i++) {
			colList.add(i + 10*col);
		}
		
		return colList;
	}
}

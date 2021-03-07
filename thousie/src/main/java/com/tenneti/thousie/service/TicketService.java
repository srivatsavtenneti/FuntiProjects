package com.tenneti.thousie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.tenneti.thousie.model.Player;
import com.tenneti.thousie.utility.ExcelConverter;
import com.tenneti.thousie.utility.HousieTicketGeneratorV2;
import com.tenneti.thousie.utility.ImgBBPhotoUpload;
import com.tenneti.thousie.utility.TicketUniqueGenerator;

@Service
public class TicketService {
	
	@Autowired
	SMSService smsService;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	TicketUniqueGenerator uniqueGenerator = new TicketUniqueGenerator();
	HousieTicketGeneratorV2 ticketGeneratorV2 = new HousieTicketGeneratorV2();
	ExcelConverter excelConverter = new ExcelConverter();
	ImgBBPhotoUpload imgUpload = new ImgBBPhotoUpload();
	
	public void generateTickets(List<Player> players) {
		int totalTickets = 0;
		int sixSets = 0;
		int balanceTickets = 0;
		List<int[][]> tickets = new ArrayList<int[][]>();

		for(Player player: players) {
			totalTickets += player.getNumTickets();
		}
		
		sixSets = totalTickets / 6;
		balanceTickets = totalTickets % 6;
		
		for(int i=0; i < sixSets; i++) {
			tickets.addAll(getTickets(6));
		}
		if(balanceTickets > 0) {
			tickets.addAll(getTickets(balanceTickets));
		}
		
		List<Integer> imageList = getEmojiListFromCache(totalTickets);
		Random random = new Random();
		List<String> housieText = new ArrayList<String>(Arrays.asList("అ","ఆ","ఇ","ఈ","ఉ","ఊ","ఋ","ౠ","ఎ","ఏ","ఐ","ఒ","ఓ","ఔ","ం","ః","క","ఖ","గ","ఘ","ఙ","చ","ఛ","జ","ఝ","ఞ","ట","ఠ","డ","ఢ","ణ","త","థ","ద","ధ","న","ప","ఫ","బ","భ","మ","య","ర","ల","వ","శ","ష","స","హ","ళ","క్ష","ఱ"));
		
		
		for(Player player: players) {
			List<int[][]> playerTickets = new ArrayList<int[][]>();
			List<Integer> randomImageList = new ArrayList<Integer>();
			List<String> randomHousieText = new ArrayList<String>();
			for(int i = 0; i<player.getNumTickets(); i++) {
				playerTickets.add(tickets.get(i));
				int imageNo = random.nextInt(imageList.size());
				randomImageList.add(imageList.get(imageNo));
				imageList.remove(imageList.get(imageNo));
				for(int j=0; j<3; j++) {
					if(housieText.size() == 0) {
						// housieText = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"));	
						housieText = new ArrayList<String>(Arrays.asList("అ","ఆ","ఇ","ఈ","ఉ","ఊ","ఋ","ౠ","ఎ","ఏ","ఐ","ఒ","ఓ","ఔ","ం","ః","క","ఖ","గ","ఘ","ఙ","చ","ఛ","జ","ఝ","ఞ","ట","ఠ","డ","ఢ","ణ","త","థ","ద","ధ","న","ప","ఫ","బ","భ","మ","య","ర","ల","వ","శ","ష","స","హ","ళ","క్ష","ఱ"));
					}
					int textNo = random.nextInt(housieText.size());
					randomHousieText.add(housieText.get(textNo));
					housieText.remove(housieText.get(textNo));
				}
			}
			
			sendTickets(playerTickets, player, randomImageList, randomHousieText);
			tickets.removeAll(playerTickets);
		}
		
		clearCache();
		if(!imageList.isEmpty()) {
			redisTemplate.opsForList().rightPushAll("emojiList", imageList);
		}
	}
	
	private List<int[][]> getTickets(int count) {
		List<int[][]> tickets = ticketGeneratorV2.generateTickets(count);
		List<int[][]> uniqueTickets = uniqueGenerator.modifyTickets(tickets);
		
		return uniqueTickets;
	}
	
	private void sendTickets(List<int[][]> playerTickets, Player player, List<Integer> imageList, List<String> randomHousieText) {
		String path = excelConverter.converToExcel(playerTickets, player.getName(), player.getNumTickets(), imageList, randomHousieText);
		String url = null;
		String ticketText = "టిక్కెట్టు";
		try {
			url = imgUpload.uploadPhoto(path);
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		if(player.getNumTickets() > 1) {
			ticketText = " టిక్కెట్లు";
		}
		smsService.sendSMS(player.getPhone(), player.getName() + ", ఇదిగో  మీ " + ticketText, url);
	}
	
	private List<Integer> getEmojiListFromCache(int totalTickets) {
		List<Integer> emojiLst = new ArrayList<Integer>();
		Random random = new Random();
		
		if(redisTemplate.hasKey("emojiList")  && redisTemplate.opsForList().size("emojiList") != 0) {
			if(redisTemplate.opsForList().size("emojiList") < totalTickets) {
				List<Integer> cacheList = redisTemplate.opsForList().range("emojiList", 0, -1);
				
				for(int i=0; i < (totalTickets - cacheList.size()); i++) {
					int imageNo = 0;
					do {
						//NOTE: 76 is IF YOU HAVE 75 EMOJIs
						imageNo = random.nextInt(101);
					} while(cacheList.contains(imageNo+1));
					redisTemplate.opsForList().rightPush("emojiList", imageNo+1);
				}
			}
			
		} else {
			
			loadEmojiListToCache();
		}
		
		emojiLst = redisTemplate.opsForList().range("emojiList", 0, -1);
		return emojiLst;
	}
	
	private void loadEmojiListToCache() {
		
		List<Integer> imageList = new ArrayList<Integer>();
		//NOTE: 76 is IF YOU HAVE 75 EMOJIs
		for(int i=1; i<101; i++) {
			imageList.add(i);
		}
		
		redisTemplate.opsForList().rightPushAll("emojiList", imageList);
	}
	
	public void clearCache() {
		redisTemplate.delete("emojiList");
	}
}

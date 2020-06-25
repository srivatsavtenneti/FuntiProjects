# Funti HOUSIE

#### Description
`This is Funti Housie, where Housie tickets are generated(tickets just not only contain numbers but also emojis and characters as well). The generated tickets are written to Excel, then put into a image , uploaded to ImgBB and will be send as an whatsapp image using Twilio. Also for Learning perspective included how to connect to Google APis using oauth.`

#### Tech Stack
	REST
	JAVA 8
	Redis
	Apache POI
	OAUTH
	SpringBoot

#### Manual
<b>PreRequisites</b>

	JDK8
	Redis as Service
	ImgBB Account(Key)
	Twilio Account(have Ids, Whatsapp Account)
	Google Api Account(Client Id and Secret)-Optional
	
	Below Folders Need To Be Created
	1. YOUR_LOCAL_PATH_TO_SAVE_EMOJIS : All Emojis(Ex:75, saved as numbers starting from 1)
	2. YOUR_LOCAL_PATH_TO_SAVE_TICEKTS

<b>Run the Application</b>
	
	open CMD/Terminal
	java -jar <PATH_OF_JAR_FILE> or
	navigate to the folder where the jar is present and run below
	java -jar <FILENAME_INCLUDING_EXTENSION>
	ex: java -jar thousie.jar


<b>Postman Request</b>

	In postman, make a post Request
	POST: localhost:8080/funtiHousie/generateTickets
	body: 
	[
		{
			"name": "<PERSON_NAME>",
			"phone": "<PHONE_NUMBER>",
			"numTickets": <NUMBER>
		},
		{
			"name": "<PERSON2_NAME>",
			"phone": "<PHONE_NUMBER2>",
			"numTickets": <NUMBER2>
		}
	]
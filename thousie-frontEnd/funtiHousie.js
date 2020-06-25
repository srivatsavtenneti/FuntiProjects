let a = document.getElementById("play");
const randomEmojiNumberList = [];
for(let i=1; i<76; i++){
	randomEmojiNumberList.push(i);
}
const codeList = ["SML", "GRN", "LOL", "SLF", "FSF", "TON", "SLP", "CBH", "PTY", "COL", "SAD", "CRY", "GST", "DSB", "DHB", "DTB", "WIN", "CRS", "HRY", "DAN", "PNT", "HIT", "NAM", "KID", "SDY", "FET", "DOG", "RCN", "TGR", "UCN", "RDR", "EPT", "MOS", "RBT", "SQL", "PAW", "BRD", "SNK", "OCP", "ANT", "MNY", "MSK", "YWN", "BMB", "CLP", "SLF", "NNY", "TAT", "PIG", "CML", "PND", "HEN", "PGN", "EGL", "DUC", "SWN", "TRX", "WHL", "DLP", "FSH", "SHK", "BLY", "XMS", "PAM", "RCE", "MPL", "MNG", "BAN", "CCT", "CRT", "PUT", "CHZ", "PIZ", "PCN", "ICM"];						

const numberList = [];
for(let i=1; i<91; i++){
	
	numberList.push(i);
}

const tempNumberList = [];

// const alphabetsList = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
const alphabetsList = ["అ","ఆ","ఇ","ఈ","ఉ","ఊ","ఋ","ౠ","ఎ","ఏ","ఐ","ఒ","ఓ","ఔ","అం","అః","క","ఖ","గ","ఘ","ఙ","చ","ఛ","జ","ఝ","ఞ","ట","ఠ","డ","ఢ","ణ","త","థ","ద","ధ","న","ప","ఫ","బ","భ","మ","య","ర","ల","వ","శ","ష","స","హ","ళ","క్ష","ఱ"];
const newAlphaList =[];
let prevAlpha =0, prevAlphaIndex = 0;
let lastAlpha =0;

function getAlphabet(){
	let randomAlphaIndex =0,  randomAlpha = 0, lastAlphaId = 0 , prevAlphaId=0;
	if(newAlphaList.length < alphabetsList.length){
		do{
			randomAlphaIndex = Math.floor(Math.random() * alphabetsList.length);
		}while(newAlphaList.indexOf(randomAlphaIndex) !== -1);
		
		if(newAlphaList.length > 0){
			if(newAlphaList.length > 1){
				lastAlpha = prevAlpha;
			
				lastAlphaId = "a" + alphabetsList.indexOf(lastAlpha);
				document.getElementById(lastAlphaId).style.backgroundColor = "skyblue";
			}
			prevAlpha = document.getElementById("alpha").innerHTML;
			
			prevAlphaId = "a" +  alphabetsList.indexOf(prevAlpha);
			document.getElementById(prevAlphaId).innerHTML = prevAlpha;
			document.getElementById(prevAlphaId).style.backgroundColor = "orange";
		} 
		randomAlpha = alphabetsList[randomAlphaIndex];
		
		document.getElementById("alpha").innerHTML = randomAlpha;
	
		newAlphaList.push(randomAlphaIndex);
	
	} else if(newAlphaList.length === alphabetsList.length){
			lastAlpha = prevAlpha;
			lastAlphaId = "a" + alphabetsList.indexOf(lastAlpha);
			document.getElementById(lastAlphaId).style.backgroundColor = "skyblue";
			
			prevAlpha = document.getElementById("alpha").innerHTML;
			prevAlphaId = "a" +  alphabetsList.indexOf(prevAlpha);
			document.getElementById(prevAlphaId).innerHTML = prevAlpha;
			document.getElementById(prevAlphaId).style.backgroundColor = "orange";
			
			let textElem = document.getElementById("alpha");
			textElem.style.fontSize = "40px";
			textElem.innerHTML = "Surprise Done!!!"
		}
}	

let count = 1; 

function getEmoji(){
					let elem = document.getElementById("play");
					let codeElem = document.getElementById("code");
	elem.parentNode.removeChild(elem);
	codeElem.parentNode.removeChild(codeElem);
	 let p = document.getElementById("srp1");
    var newElement = document.createElement("img");
    newElement.setAttribute('id', "play");
	newElement.setAttribute('class',"fun");
	
	var newCodeElement = document.createElement("div");
    newCodeElement.setAttribute('id', "code");
	newCodeElement.setAttribute('class',"fun");
	
	p.appendChild(newElement);	
	p.appendChild(newCodeElement);

	if(randomEmojiNumberList.length === 0){
		document.getElementById("code").innerHTML = "Emoji Done!";
	}else{
		document.getElementById("counter").innerHTML = count;
		count++;
		setTimeout(function() {
			const randomIndex = Math.floor(Math.random() * randomEmojiNumberList.length);
			const randomEmoji = randomEmojiNumberList[randomIndex];
	
			let path = '<YOUR_LOCAL_PATH_TO_SAVE_EMOJIS>' +randomEmoji+'.png';
			document.getElementById("play").src = path;


			document.getElementById("play").style.right = "10px";
			document.getElementById("code").style.right = "10px";
			const codeNumber = codeList[randomIndex];
			document.getElementById("code").innerHTML = codeNumber;
			console.log("code: " +codeNumber);
			randomEmojiNumberList.splice(randomIndex,1);
			codeList.splice(randomIndex,1);
		}, 25);
	}
	
	
}							
let prevNumber = 0, prevNumberIndex = 0; 
let beforeIndex = 0;


function getNumber(){
	let randomNumberIndex, randomNumber=0;
	if(tempNumberList.length <90){
		
	
		do{
			randomNumberIndex = Math.floor(Math.random() * numberList.length);
			console.log("condition: " + tempNumberList.indexOf(randomNumber));
		} while(tempNumberList.indexOf(randomNumberIndex) !== -1);
		
		if(tempNumberList.length > 0){
			if(tempNumberList.length > 1){
				
				beforeIndex = prevNumberIndex;
				document.getElementById(beforeIndex).style.backgroundColor = "aqua";
			}
			prevNumber = document.getElementById("number").innerHTML;
			prevNumberIndex = prevNumber - 1;
			document.getElementById(prevNumberIndex).innerHTML = prevNumber;
			document.getElementById(prevNumberIndex).style.backgroundColor = "lime";
		}
		randomNumber = numberList[randomNumberIndex];
			
			
			let p = document.getElementById("data");
			let num = document.getElementById("number");
			num.parentNode.removeChild(num);
		
			
			let spinnerElement = document.createElement("div");
			spinnerElement.setAttribute('class',"spinner");
			spinnerElement.setAttribute('id',"spin");
			p.appendChild(spinnerElement);	
		setTimeout(() => {
			let elem = document.getElementById("spin");
			elem.parentNode.removeChild(elem);
			
			let numberElement = document.createElement("div");
			numberElement.setAttribute('id',"number");
			p.appendChild(numberElement);	
			document.getElementById("number").innerHTML = randomNumber;
			
		},1000);
		
		
		
		console.log("index: " + randomNumberIndex);
		console.log("number: " + randomNumber);
		
		tempNumberList.push(randomNumberIndex);
	} 	
		else if(tempNumberList.length === 90){
	
			beforeIndex = prevNumberIndex;
			document.getElementById(beforeIndex).style.backgroundColor = "aqua";
		
			prevNumber = document.getElementById("number").innerHTML;
			prevNumberIndex = prevNumber - 1;
			document.getElementById(prevNumberIndex).innerHTML = prevNumber;
			document.getElementById(prevNumberIndex).style.backgroundColor = "lime";
			document.getElementById("number").innerHTML = "Board Completed!!";		
		} 
	
	
}
								
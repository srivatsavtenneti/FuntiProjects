package com.tenneti.thousie.model;

import java.util.HashMap;
import java.util.Map;

public enum EmojiCode {
	SML(1, "SML"), GRN(2, "GRN"), LOL(3, "LOL"), SLF(4, "SLF"), FSF(5, "FSF"), TON(6, "TON"), SLP(7, "SLP"), CBH(8, "CBH"), PTY(9, "PTY"), COL(10, "COL"), SAD(11, "SAD"), CRY(12, "CRY"), GST(13, "GST"), DSB(14, "DSB"),
	DHB(15, "DHB"), DTB(16, "DTB"), WIN(17, "WIN"), CRS(18, "CRS"), HRY(19, "HRY"), DAN(20, "DAN"), PNT(21, "PNT"), HIT(22, "HIT"), NAM(23, "NAM"), KID(24, "KID"), SDY(25, "SDY"), FET(26, "FET"), DOG(27, "DOG"),
	RCN(28, "RCN"), TGR(29, "TGR"), UCN(30, "UCN"), RDR(31, "RDR"), EPT(32, "EPT"), MOS(33, "MOS"), RBT(34, "RBT"), SQL(35, "SQL"), PAW(36, "PAW"), BRD(37, "BRD"), SNK(38, "SNK"), OCP(39, "OCP"), ANT(40, "ANT"), MNY(41, "MNY"), MSK(42, "MSK"), YWN(43, "YWN"), BMB(44, "BMB"), CLP(45, "CLP"), SFI(46, "SFI"), NNY(47, "NNY"), TAT(48, "TAT"), PIG(49, "PIG"), CML(50, "CML"), PND(51, "PND"), HEN(52, "HEN"), PGN(53, "PGN"), EGL(54, "EGL"), DUC(55, "DUC"), SWN(56, "SWN"), TRX(57, "TRX"), WHL(58, "WHL"), DLP(59, "DLP"), FSH(60, "FSH"), SHK(61, "SHK"), BLY(62, "BLY"), XMS(63, "XMS"), PAM(64, "PAM"), RCE(65, "RCE"), MPL(66, "MPL"), MNG(67, "MNG"), BAN(68, "BAN"), CCT(69, "CCT"), CRT(70, "CRT"), PUT(71, "PUT"), CHZ(72, "CHZ"), PIZ(73, "PIZ"), PCN(74, "PCN"), ICM(75, "ICM");

	public final int code;
	public final String text;
	 private static Map<Integer, String> map = new HashMap<Integer, String>();

	private EmojiCode(int code, String text) {
		this.code = code;
		this.text = text;
	}
	
	 static {
		 for (EmojiCode emojiCode : EmojiCode.values()) {
			 map.put(emojiCode.code, emojiCode.text);
		 }
	 }
	 
	 public static String getTextFromCode(int code) {
	        return (String) map.get(code);
	    }

	 public int getCode() {
		 return code;
	 }
	 
	 public String getText() {
		 return text;
	 }

}

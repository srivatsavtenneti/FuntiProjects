package com.tenneti.thousie.model;

import java.util.HashMap;
import java.util.Map;

public enum EmojiCode {
	FTY(1, "FTY"), GST(2, "GST"), STY(3, "STY"), DOG(4, "DOG"), DER(5, "DER"), EPT(6, "EPT"), MOS(7, "MOS"), KGR(8, "KGR"), MSQ(9, "MSQ"), BRD(10, "BRD"), SNK(11, "SNK"), SPN(12, "SPN"), ANT(13, "ANT"), MNY(14, "MNY"), BRS(15, "BRS"), TRK(16, "TRK"), PCK(17, "PCK"), HEN(18, "HEN"), PGN(19, "PGN"), EGL(20, "EGL"), DCK(21, "DCK"), SWN(22, "SWN"), DGN(23, "DGN"), WHL(24, "WHL"), DLP(25, "DLP"), FSH(26, "FSH"), SHK(27, "SHK"), PPR(28, "PPR"), PAM(29, "PAM"), CRN(30, "CRN"), PPL(31, "PPL"), GRP(32, "GRP"), CCT(33, "CCT"), CRT(34, "CRT"), BCH(35, "BCH"), TOM(36, "TOM"), BUS(37, "BUS"), ICM(38, "ICM"), HFV(39, "HFV"), TCR(40, "TCR"), FMR(41, "FMR"), CHF(42, "CHF"), MEC(43, "MEC"), FWR(44, "FWR"), DOC(45, "DOC"), SNT(46, "SNT"), ITW(47, "ITW"), ART(48, "ART"), PLT(49, "PLT"), AST(50, "AST"), SCS(51, "SCS"), HBS(52, "HBS"), BJL(53, "BJL"), GLC(54, "GLC"), ONN(55, "ONN"), POP(56, "POP"), MTN(57, "MTN"), BRC(58, "BRC"), TPL(59, "TPL"), TRN(60, "TRN"), ATO(61, "ATO"), CAR(62, "CAR"), CRS(63, "CRS"), BEE(64, "BEE"), SHP(65, "SHP"), SUN(66, "SUN"), MON(67, "MON"), FRE(68, "FRE"), SNW(69, "SNW"), SNF(70, "SNF"), CKE(71, "CKE"), CCL(72, "CCL"), WMN(73, "WMN"), BCL(74, "BCL"), PEN(75, "PEN"), SPL(76, "SPL"), NBK(77, "NBK"), CFE(78, "CFE"), UMB(79, "UMB"), MHM(80, "MHM"), TTS(81, "TTS"), HRS(82, "HRS"), MDL(83, "MDL"), CKT(84, "CKT"), STL(85, "STL"), KTE(86, "KTE"), DCE(87, "DCE"), BEL(88, "BEL"), DPM(89, "DPM"), BLN(90, "BLN"), SRL(91, "SRL"), TSL(92, "TSL"), FLG(93, "FLG"), CDL(94, "CDL"), CRB(95, "CRB"), SMN(96, "SMN"), WZD(97, "WZD"), ZBR(98, "ZBR"), COW(99, "COW"), RNO(100, "RNO");

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

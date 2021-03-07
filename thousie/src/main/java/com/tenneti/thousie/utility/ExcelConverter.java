package com.tenneti.thousie.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tenneti.thousie.model.EmojiCode;
import com.tenneti.thousie.model.RandomHousieObject;

public class ExcelConverter {

	private static String FILE_DIR = "C:\\Users\\nowdu\\workspace\\Funti Housie Backend\\Assets\\ExcelTickets\\";
	private static String FILE_EXT = ".xlsx";
	private static String EMOJI_FILE_DIR = "C:\\Users\\nowdu\\workspace\\Funti Housie Backend\\Assets\\Emojis\\";
	private static String EMOJI_FILE_EXT = ".png";

	ExcelToImageConverter imageConverter = new ExcelToImageConverter();

	public String converToExcel(List<int[][]> data, String name, int count, List<Integer> imageList, List<String> housieText) {
		String path = null;
		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet("tickets");
	
		int[] rowNum = getRowNum(count);

		short colorIndex = getRandomcolorIndex();
		int[] ticketNumber = { 1 };
		Random random = new Random();
		data.forEach((ticket) -> {
			int imageNo = random.nextInt(imageList.size());
			XSSFRow codeRow = sheet.createRow(rowNum[0]-1);
			
			if(count == 6) {
				codeRow.setHeightInPoints(20);
			} else {
				codeRow.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
			}
			
			Cell codeCell = codeRow.createCell(1);
			codeCell.setCellValue(EmojiCode.getTextFromCode(imageList.get(imageNo)));
			CellRangeAddress cellRangeAddress = new CellRangeAddress(rowNum[0]-1, rowNum[0]-1, 1, 10);
			sheet.addMergedRegion(cellRangeAddress);
			codeCell.setCellStyle(getCellTicketNumberStyle(workbook, count));
			RandomHousieObject object = getEmojis(ticket, workbook, sheet, rowNum[0], imageNo, imageList);
			for (int i = 0; i < 3; i++) {
				XSSFRow row = sheet.createRow(rowNum[0]);
				if(count > 3) {
					row.setHeightInPoints((2 * sheet.getDefaultRowHeightInPoints()));
				} else {
					row.setHeightInPoints((4 * sheet.getDefaultRowHeightInPoints()));
				}
				
				if(i == 1) {
					sheet.setColumnWidth(0,1000);
					Cell ticketNumberCell = row.createCell(0);
					ticketNumberCell.setCellValue(ticketNumber[0]);
					ticketNumberCell.setCellStyle(getCellTicketNumberStyle(workbook, count));
					ticketNumber[0]++;
				}
				
				for (int j = 0; j < 9; j++) {
					if(j == 0) {
						sheet.setColumnWidth(j+1, 1250);
					} else {
						sheet.setColumnWidth(j+1, 2200);
					}
					XSSFCell cell = row.createCell(j+1);
					cell.setCellStyle(getCellStyle(workbook, colorIndex, count));
					if (ticket[j][i] == 0) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(ticket[j][i]);
					}
				}
				rowNum[0]++;
			}
			setHousieCells(ticket, sheet, rowNum[0],object, housieText, workbook, count, colorIndex);
			
			rowNum[0]++;
			
		});
		
		 for (int i = 1; i < 10; i++) {
	            sheet.autoSizeColumn(i);
	        }

		try {
			String fileName = FILE_DIR + name + FILE_EXT;
			FileOutputStream output = new FileOutputStream(new File(fileName));
			workbook.write(output);
			output.close();
			workbook.close();
			path = imageConverter.convertToImage(fileName, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
	
	private RandomHousieObject getEmojis(int[][] ticket, XSSFWorkbook workbook, XSSFSheet sheet, int rowNum, int imageNo, List<Integer> imageList) {
		RandomHousieObject object = new RandomHousieObject();
		Random random = new Random();
		int singleRow = random.nextInt(3);
		int singleCol = 0;
		for(int r=0; r<3; r++) {
			List<Integer> emptyCellList = new ArrayList<Integer>();
			for(int i=0; i<9; i++) {
				if(ticket[i][r] == 0) {
					emptyCellList.add(i);
				}
			}
			if(singleRow == r) {
				int cellIndex = random.nextInt(emptyCellList.size());
				getEmojiIcon(workbook, sheet, rowNum+r, emptyCellList.get(cellIndex)+1, imageList.get(imageNo));
				singleCol =  emptyCellList.get(cellIndex)+1;
			}
		}
		object.setRow(rowNum + singleRow + 1);
		object.setColumn(singleCol);
		imageList.remove(imageList.get(imageNo));
		
		return object;
	}
	
	private void setHousieCells(int[][] ticket, XSSFSheet sheet, int rowNum, RandomHousieObject object , List<String> housieText, XSSFWorkbook workbook, int count, short colorIndex) {
		Random random = new Random();
		List<String> ticketText = new ArrayList<String>();
		for(int r=0; r<3; r++) {
			List<Integer> emptyCellList = new ArrayList<Integer>();
			for(int i=0; i<9; i++) {
				if(ticket[i][r] == 0) {
					emptyCellList.add(i);
				}
			}
			
			int singleCol = 0;
			int housieIndex  = 0;
			int previousCol = 0;
			do {
				singleCol = random.nextInt(emptyCellList.size());
				housieIndex = random.nextInt(housieText.size());
			} while ((rowNum-2+r == object.getRow() && emptyCellList.get(singleCol)+1 == object.getColumn()) || (previousCol == singleCol) || (ticketText.contains(housieText.get(housieIndex))));
			updateCell(sheet, rowNum-3+r, emptyCellList.get(singleCol)+1, housieText.get(housieIndex), workbook, count, colorIndex);
			previousCol = singleCol;
			ticketText.add(housieText.get(housieIndex));
			emptyCellList.remove(emptyCellList.get(singleCol));
			housieText.remove(housieText.get(housieIndex));					
		}	
	}
	
	private void updateCell(XSSFSheet sheet, int rowNum, int cellNum, String letter, XSSFWorkbook workbook, int count, short colorIndex) {
		XSSFRow row = sheet.getRow(rowNum);
		Cell cell = row.getCell(cellNum);
		cell.setCellValue(letter);
		
		short border = XSSFCellStyle.BORDER_MEDIUM;
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(border);
		style.setBorderLeft(border);
		style.setBorderTop(border);
		style.setBorderRight(border);
		style.setFillForegroundColor(colorIndex);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(getTextFont(workbook, count));
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		cell.setCellStyle(style);	
	}
	
	
	
	private int[] getRowNum(int count) {
		int[] rowNum = { 1 };
		switch(count) {
			case 1: 
				rowNum[0] = 15;
				break;
			case 2:
				rowNum[0] = 9;
				break;
			case 3:
				rowNum[0] = 3;
				break;
			case 4:
				rowNum[0] = 7;
				break;
			case 5:
				rowNum[0] = 5;
				break;
			default:
				rowNum[0] = 1;
		}
		return rowNum;
	}
	
	private XSSFCellStyle getCellTicketNumberStyle(XSSFWorkbook workbook, int count) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(getFont(workbook, count));
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}

	private XSSFCellStyle getCellStyle(XSSFWorkbook workbook, short colorIndex, int count) {
		short border = XSSFCellStyle.BORDER_MEDIUM;
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(border);
		style.setBorderLeft(border);
		style.setBorderTop(border);
		style.setBorderRight(border);
		style.setFillForegroundColor(colorIndex);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(getFont(workbook, count));
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}
	
	private XSSFFont getTextFont(XSSFWorkbook workbook, int count) {
		XSSFFont font= workbook.createFont();
		if(count == 6) {
			 font.setFontHeightInPoints((short)22);
		} else if (count == 5) {
			font.setFontHeightInPoints((short)24);
		} else if(count == 4) {
			font.setFontHeightInPoints((short)26);
		} else {
			 font.setFontHeightInPoints((short)26);
		}
	   
	    font.setFontName("Comic Sans MS");
	    font.setColor(IndexedColors.DARK_RED.getIndex());
	    font.setBold(true);
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    font.setItalic(false);
	    
	    return font;
	}
	
	private XSSFFont getFont(XSSFWorkbook workbook, int count) {
		XSSFFont font= workbook.createFont();
		if(count == 6) {
			 font.setFontHeightInPoints((short)20);
		} else if (count == 5) {
			font.setFontHeightInPoints((short)22);
		} else if(count == 4) {
			font.setFontHeightInPoints((short)24);
		} else {
			 font.setFontHeightInPoints((short)26);
		}
	   
	    font.setFontName("Comic Sans MS");
	    font.setColor(IndexedColors.BLACK.getIndex());
	    font.setBold(true);
	    font.setItalic(false);
	    
	    return font;
	}
	
	
	private short getRandomcolorIndex() {
		List<Short> colorIndexes = new ArrayList<Short>();
		colorIndexes.add(IndexedColors.WHITE.getIndex());
		colorIndexes.add(IndexedColors.LIGHT_YELLOW.getIndex());
		colorIndexes.add(IndexedColors.SKY_BLUE.getIndex());
		colorIndexes.add(IndexedColors.GREY_25_PERCENT.getIndex());
		colorIndexes.add(IndexedColors.LEMON_CHIFFON.getIndex());
		
		Random random = new Random();
		int index = random.nextInt(colorIndexes.size());
		return colorIndexes.get(index);
	}
	
	private void getEmojiIcon(XSSFWorkbook workbook, XSSFSheet sheet, int row, int col, int imageNo) {
		
		   InputStream inputStream;
		try {
			inputStream = new FileInputStream(EMOJI_FILE_DIR + imageNo + EMOJI_FILE_EXT);
		
		   byte[] bytes = IOUtils.toByteArray(inputStream);
		   int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
		   inputStream.close();
		 
		   XSSFDrawing drawing = sheet.createDrawingPatriarch();
		 
		   ClientAnchor anchor = new XSSFClientAnchor();

		   anchor.setCol1(col);
		   anchor.setRow1(row);
		   anchor.setCol2(col+1);
		   anchor.setRow2(row+1);
		    
		   drawing.createPicture(anchor, pictureIdx);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

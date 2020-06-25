package com.tenneti.thousie.utility;

import com.aspose.cells.ImageFormat;
import com.aspose.cells.ImageOrPrintOptions;
import com.aspose.cells.SheetRender;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

public class ExcelToImageConverter {

	private static String FILE_DIR = "<YOUR_LOCAL_PATH_TO_SAVE_TICEKTS>";
	private static String FILE_EXT = ".png";

	public String convertToImage(String pathName, String name) {
		String path = null;
		try {
			Workbook workbook = new Workbook(pathName);
			ImageOrPrintOptions imgOptions = new ImageOrPrintOptions();
			imgOptions.setImageFormat(ImageFormat.getPng());

			Worksheet sheet = workbook.getWorksheets().get(0);
			sheet.setZoom(150);

			SheetRender render = new SheetRender(sheet, imgOptions);
			render.toImage(0, FILE_DIR + name + FILE_EXT);
			path = FILE_DIR + name + FILE_EXT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}
}

/**
 *
 */
package com.mk.fl.bibgyani.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.mk.fl.bibgyani.model.Question;

/**
 * @author Mouli Kalakota
 */
public class ExcelUtil {

	public static List<Question> readExcel(InputStream stream)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook workbook = WorkbookFactory.create(stream);
		Sheet sheet = workbook.getSheetAt(0);

		int noOfRows = sheet.getLastRowNum() + 1;
		List<Question> questions = new ArrayList<Question>(noOfRows);

		for (int i = 1; i < noOfRows; i++) {
			Row row = sheet.getRow(i);
			Question question = new Question();
			Cell cell = row.getCell(1);
			if (cell.getStringCellValue().isEmpty()) {
				break;
			}
			// question name in Telugu
			cell = row.getCell(0);
			question.setNameTelugu(new String(cell.getStringCellValue().getBytes("utf8"), "utf8"));
			// question name in English
			cell = row.getCell(1);
			question.setName(cell.getStringCellValue());
			// option A in Telugu
			question.setOptionATelugu(getOptionValue(row.getCell(2), true));
			// option A in English
			question.setOptionA(getOptionValue(row.getCell(3), false));
			// option B in Telugu
			question.setOptionBTelugu(getOptionValue(row.getCell(4), true));
			// option B in English
			question.setOptionB(getOptionValue(row.getCell(5), false));
			// option C in Telugu
			question.setOptionCTelugu(getOptionValue(row.getCell(6), true));
			// option C in English
			question.setOptionC(getOptionValue(row.getCell(7), false));
			// option D in Telugu
			question.setOptionDTelugu(getOptionValue(row.getCell(8), true));
			// option D in English
			question.setOptionD(getOptionValue(row.getCell(9), false));
			// answer
			cell = row.getCell(10);
			question.setAnswer(Double.valueOf(cell.getNumericCellValue()).intValue());
			// extra amount at
			cell = row.getCell(11);
			question.setAmountExtraAt(Double.valueOf(cell.getNumericCellValue()).shortValue());
			// level
			cell = row.getCell(12);
			question.setLevel(Double.valueOf(cell.getNumericCellValue()).intValue());

			questions.add(question);
		}

		return questions;
	}

	private static String getOptionValue(Cell cell, boolean isTelugu) throws UnsupportedEncodingException {
		if (null == cell) {
			throw new IllegalArgumentException("cell is undefined");
		}
		if (cell.getCellType() == 0) {
			double numericValue = cell.getNumericCellValue();
			if (numericValue == Math.floor(numericValue) && !Double.isInfinite(numericValue)) {
				return String.valueOf(Math.round(numericValue));
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		} else {
			if (isTelugu) {
				return new String(cell.getStringCellValue().getBytes("utf8"), "utf8");
			} else {
				return new String(cell.getStringCellValue().trim());
			}
		}
	}

}

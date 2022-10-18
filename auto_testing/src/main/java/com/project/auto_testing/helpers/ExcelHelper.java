package com.project.auto_testing.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import com.project.auto_testing.common.TestCaseColIndex;
import com.project.auto_testing.models.TestCase;

public class ExcelHelper {

	private static Workbook getWorkbook(InputStream inputStream, String filePath) throws IOException {
		Workbook workbook = null;
		if (filePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (filePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}

		return workbook;
	}

	private static Object getCellValue(Cell cell) {
		CellType cellType = cell.getCellType();
		Object cellValue = null;
		switch (cellType) {
		case BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case FORMULA:
			Workbook workbook = cell.getSheet().getWorkbook();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			cellValue = evaluator.evaluate(cell).getNumberValue();
			break;
		case NUMERIC:
			cellValue = cell.getNumericCellValue();
			break;
		case STRING:
			cellValue = cell.getStringCellValue();
			break;
		case _NONE:
		case BLANK:
		case ERROR:
			break;
		default:
			break;
		}

		return cellValue;
	}

	public static LinkedHashMap<Integer, TestCase> readFile(String filePath) throws IOException {
		LinkedHashMap<Integer, TestCase> data = new LinkedHashMap<Integer, TestCase>();

		InputStream inStream = new FileInputStream(new File(filePath));

		Workbook workbook = getWorkbook(inStream, filePath);

		Sheet sheet = workbook.getSheetAt(0);

		// Get all rows
		Iterator<Row> rows = sheet.iterator();
		while (rows.hasNext()) {
			Row nextRow = rows.next();
			if (nextRow.getRowNum() == 0) {
				// Ignore header
				continue;
			}
			// Get all cells
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			// Read cells and set value for Test Case object
			TestCase obj = new TestCase();
			while (cellIterator.hasNext()) {
				// Read cell
				Cell cell = cellIterator.next();
				Object cellValue = getCellValue(cell);
				if (cellValue == null || cellValue.toString().isEmpty()) {
					continue;
				}
				// Set value for Test Case object
				int columnIndex = cell.getColumnIndex();
				switch (columnIndex) {
				case TestCaseColIndex.TEST_CASE_ID:
					obj.setTestcase((String) getCellValue(cell));
					break;
				case TestCaseColIndex.SCREEN:
					obj.setScreen((String) getCellValue(cell));
					break;
				case TestCaseColIndex.STEP:
					obj.setStep((String) getCellValue(cell));
					break;
				case TestCaseColIndex.URL:
					obj.setUrl((String) getCellValue(cell));
					break;
				case TestCaseColIndex.DESCRIPTION:
					obj.setDescription((String) getCellValue(cell));
					break;
				case TestCaseColIndex.LOCATE_TYPE:
					obj.setLocateType((String) getCellValue(cell));
					break;
				case TestCaseColIndex.LOCATE_VALUE:
					obj.setLocateValue((String) getCellValue(cell));
					break;
				case TestCaseColIndex.ACTION:
					obj.setAction((String) getCellValue(cell));
					break;
				case TestCaseColIndex.AGRUMENT:
					obj.setAgrument((String) getCellValue(cell));
					break;
				case TestCaseColIndex.EXPECTED_RESULT:
					obj.setExpectedResult((String) getCellValue(cell));
					break;
				case TestCaseColIndex.SLEEP_TIME:
					obj.setMaxSleepTime((Double) getCellValue(cell));
					break;
				default:
					break;
				}
				data.put(nextRow.getRowNum(), obj);
			}
		}
		workbook.close();
		inStream.close();
		return data;
	}

	public static void createDocument(OutputStream outStream, LinkedHashMap<Integer, TestCase> data)
			throws IOException {
		try {
			InputStream inStream = new FileInputStream(
					new File("src/main/resources/report/template/Report_Template.xlsx"));
			Context context = new Context();
			context.putVar("cases", data.values().toArray());
			JxlsHelper.getInstance().processTemplate(inStream, outStream, context);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeAndFlushOutput(outStream);
		}
	}

	private static void closeAndFlushOutput(OutputStream outStream) {
		try {
			outStream.flush();
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createReport(LinkedHashMap<Integer, TestCase> data) throws FileNotFoundException {
		OutputStream outStream = new FileOutputStream("target/Test_Report.xlsx");
		try {
			createDocument(outStream, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

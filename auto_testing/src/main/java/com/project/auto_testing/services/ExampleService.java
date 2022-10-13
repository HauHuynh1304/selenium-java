package com.project.auto_testing.services;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.auto_testing.helpers.ActionHelper;
import com.project.auto_testing.helpers.ExcelHelper;
import com.project.auto_testing.models.TestCase;

@Service
public class ExampleService {
	
	private final static String filePath = "D:\\project\\selenium\\selenium-java\\test_case_example\\test_case.xlsx";

	@Autowired
	private ActionHelper actionHelper;
	
	public String demo() {
		try {
			LinkedHashMap<Integer, TestCase> data = ExcelHelper.readFile(filePath);
			data = ExcelHelper.readFile(filePath);

			for (Map.Entry<Integer, TestCase> entry : data.entrySet()) {
				actionHelper.action(entry.getValue());
			}
			ExcelHelper.createReport(data);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
}

package com.project.auto_testing.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.auto_testing.services.ExampleService;

@RestController
public class ExcutedTestCases {

	@Autowired
	private ExampleService exampleService;

	/*
	 * Demo API
	 */
	@GetMapping("/excuted-test-cases")
	public void Demo() {
		try {
			exampleService.demo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

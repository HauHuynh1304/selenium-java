package com.project.auto_testing.controllers;

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
	public String Demo() {
		return exampleService.demo();
	}
	
}

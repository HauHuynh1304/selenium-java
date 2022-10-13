package com.project.auto_testing.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestCase {
	
	private String testcase;

	private String screen;

	private String step;

	private String url;

	private Double maxSleepTime;

	private String description;

	private String locateType;

	private String locateValue;

	private String action;

	private String agrument;

	private String expectedResult;

	// User for report
	private String actualResult;

	// User for report
	private String status;
	
}

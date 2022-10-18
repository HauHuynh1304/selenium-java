package com.project.auto_testing.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BrowserTypes {
	
	CHROME("chrome"),
	FIREFOX("firefox"),
	OPERA("opera"),
	EDGE("edge");
	
	private String value;
	
}

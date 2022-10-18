package com.project.auto_testing.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnums {

	OK("OK"), NG("NG");

	private String value;

}

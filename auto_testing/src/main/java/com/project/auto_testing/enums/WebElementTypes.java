package com.project.auto_testing.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WebElementTypes {

	VALUE("value"), CLASS("class"), TYPE("type");

	private String value;

}

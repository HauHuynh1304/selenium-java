package com.project.auto_testing.helpers;

import com.project.auto_testing.enums.StatusEnums;

public class VerifyStatusHelper {

	public static String verifyStatus(String expetedResult, String actualResult) {
		return (expetedResult.equals(actualResult)) ? StatusEnums.OK.getValue() : StatusEnums.NG.getValue();
	}

	public static String verifyNullResult(String actualResult) {
		return actualResult.isEmpty() ? StatusEnums.OK.getValue() : StatusEnums.NG.getValue();
	}
}

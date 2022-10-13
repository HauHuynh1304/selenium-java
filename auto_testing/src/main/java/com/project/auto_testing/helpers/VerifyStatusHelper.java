package com.project.auto_testing.helpers;

public class VerifyStatusHelper {

	public static String verifyStatus(String expetedResult, String actualStatus) {
		return (expetedResult.equals(actualStatus)) ? "OK" : "NG";
	}
	
}

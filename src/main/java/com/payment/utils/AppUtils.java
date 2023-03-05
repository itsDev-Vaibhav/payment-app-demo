package com.payment.utils;

import java.util.Random;

public interface AppUtils {
	
	String ACTIVE_STATUS = "Active";

	
	
	static String generatePin() {
		StringBuilder strbr = new StringBuilder();
		for (int i = 1; i < 5; i++) {
			strbr.append(new Random().nextInt(9));
		}
		return strbr.toString();
	}
	
	

}

package com.incture.zp.ereturns.utils;

import org.springframework.stereotype.Component;

@Component
public class RulesUtil {

	public String defineReasonRule(String reason) {
		String pendingWith = "";
		if(reason.equalsIgnoreCase("PDF")) {
			pendingWith = "S0019321677";
		} else {
			pendingWith = "S0019321678";
		}
		return pendingWith;
	}
	
	public String defineValueRule(String value) {
		String pendingWith = "";
		int val = Integer.parseInt(value);
		if(val > 100) {
			pendingWith = "S0019321678";
		} else {
			pendingWith = "S0019321677";
		}
		return pendingWith;
	}
}

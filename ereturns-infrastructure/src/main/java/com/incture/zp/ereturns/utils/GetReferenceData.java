package com.incture.zp.ereturns.utils;

import java.util.Calendar;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class GetReferenceData {
	
	public String execute(String type) {
		Calendar now = Calendar.getInstance();
		String year = now.get(Calendar.YEAR) + "";
		String month = String.valueOf(now.get(Calendar.MONTH) + 1);
		if (month.length() != 2) {
			month = "0" + month;
		}
		return type + year.substring(2, 4) + month;
	}

	public String getNextSeqNumber(String referenceCode, int noOfDigits, SessionFactory sessionFactory) {
		return SequenceNumberGen.getInstance().getNextSeqNumber(
				referenceCode, noOfDigits, sessionFactory.getCurrentSession());
	}

}

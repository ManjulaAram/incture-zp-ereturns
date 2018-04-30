package com.incture.zp.ereturns.utils;

import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {

	public static boolean isEmpty(Object o) {
		if (o == null || (o instanceof Long && (Long)o == 0L)) {
			return true;
		}
		return false;
	}

}

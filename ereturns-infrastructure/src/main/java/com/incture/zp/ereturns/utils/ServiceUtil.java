package com.incture.zp.ereturns.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtil.class);
	
	public static boolean isEmpty(Object o) {
		if (o == null || (o instanceof Long && (Long)o == 0L)) {
			return true;
		}
		return false;
	}
	
//	public static DestinationConfiguration getDest(String destinationName) {
//		if (!ServiceUtil.isEmpty(destinationName)) {
//			try {
//				Context ctx = new InitialContext();
//				ConnectivityConfiguration configuration =
//						(ConnectivityConfiguration) ctx.lookup("java:comp/env/connectivityConfiguration");
//				DestinationConfiguration destConfiguration = configuration.getConfiguration(destinationName);
//				return destConfiguration;
//			} catch (Exception e) {
//				LOGGER.error("Workflow getting destination error:" + e.getMessage());
//			}
//		}
//		return null;
//	}

}

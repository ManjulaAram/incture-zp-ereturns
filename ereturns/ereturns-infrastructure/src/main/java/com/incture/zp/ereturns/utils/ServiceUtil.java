package com.incture.zp.ereturns.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

@Component
public class ServiceUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtil.class);
	
	private static Pattern regexPattern;
	private static Matcher regMatcher;

	public static boolean isEmpty(Object o) {
		if (o == null || (o instanceof Long && (Long) o == 0L)) {
			return true;
		}
		return false;
	}

	public static DestinationConfiguration getDest(String destinationName) {
		if (!ServiceUtil.isEmpty(destinationName)) {
			try {
				Context ctx = new InitialContext();
				ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx
						.lookup("java:comp/env/connectivityConfiguration");
				DestinationConfiguration destConfiguration = configuration.getConfiguration(destinationName);
				return destConfiguration;
			} catch (Exception e) {
				LOGGER.error("Workflow getting destination error:" + e.getMessage());
			}
		}
		return null;
	}

	public static boolean isValidEmailAddress(String emailAddress) {
		regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
		regMatcher = regexPattern.matcher(emailAddress);
		if (regMatcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
}

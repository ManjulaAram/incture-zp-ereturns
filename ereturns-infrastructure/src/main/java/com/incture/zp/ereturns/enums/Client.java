package com.incture.zp.ereturns.enums;

public enum Client {
	MOBILE_A, MOBILE_I, WEB, FIORI;
	
	/**
	 * This method gets the application client.
	 */
	public String getClient() {
		String client = null;
		switch (this) {
		case MOBILE_A:
				client = "ANDROID";
				break;
		case MOBILE_I:
				client = "IOS";
				break;
		case WEB:
				client = "WEB";
				break;
		case FIORI:
			client = "FIORI";
			break;

		default:
			throw new IllegalArgumentException();
		}
		return client;
	}
}

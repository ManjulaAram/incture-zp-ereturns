package com.incture.zp.ereturns.dto;

public class PriceOverrideDto {

	private String overridePrice;
	
	private String itemCode;
	
	private String requestId;

	public String getOverridePrice() {
		return overridePrice;
	}

	public void setOverridePrice(String overridePrice) {
		this.overridePrice = overridePrice;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
}

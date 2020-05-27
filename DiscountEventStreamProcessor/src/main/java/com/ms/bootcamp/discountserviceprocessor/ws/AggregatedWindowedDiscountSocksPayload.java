package com.ms.bootcamp.discountserviceprocessor.ws;

public class AggregatedWindowedDiscountSocksPayload {
	private String category;
	private String windowTotal;
	private String windowStart;
	private String windowEnd;
	
	
	
	public AggregatedWindowedDiscountSocksPayload() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AggregatedWindowedDiscountSocksPayload(String category, String windowTotal, String windowStart,
			String windowEnd) {
		super();
		this.category = category;
		this.windowTotal = windowTotal;
		this.windowStart = windowStart;
		this.windowEnd = windowEnd;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getWindowTotal() {
		return windowTotal;
	}
	public void setWindowTotal(String windowTotal) {
		this.windowTotal = windowTotal;
	}
	public String getWindowStart() {
		return windowStart;
	}
	public void setWindowStart(String windowStart) {
		this.windowStart = windowStart;
	}
	public String getWindowEnd() {
		return windowEnd;
	}
	public void setWindowEnd(String windowEnd) {
		this.windowEnd = windowEnd; 
	}
	
	

}

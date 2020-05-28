package com.ms.bootcamp.discountserviceprocessor.ws;

public class WindowedDiscountByInstanceSocksPayload {

	private String category;
	private String discountApplied;
	private String timestamp;
	private String windowStart;
	private String windowEnd;

	public WindowedDiscountByInstanceSocksPayload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(String discountApplied) {
		this.discountApplied = discountApplied;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
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

	public WindowedDiscountByInstanceSocksPayload(String category, String discountApplied, String timestamp,
			String windowStart, String windowEnd) {
		super();
		this.category = category;
		this.discountApplied = discountApplied;
		this.timestamp = timestamp;
		this.windowStart = windowStart;
		this.windowEnd = windowEnd;
	}

	@Override
	public String toString() {
		return "WindowedDiscountByInstanceSocksPayload [category=" + category + ", discountApplied=" + discountApplied
				+ ", timestamp=" + timestamp + ", windowStart=" + windowStart + ", windowEnd=" + windowEnd + "]";
	}

}

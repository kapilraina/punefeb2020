package com.ms.bootcamp.discountserviceprocessor.ws;

public class WindowedDiscountByInstanceSocksPayload {

	private String category;
	private String discountApplied;
	private long timestamp;
	private String formattedTimestamp;
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

	

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getFormattedTimestamp() {
		return formattedTimestamp;
	}

	public void setFormattedTimestamp(String formattedTimestamp) {
		this.formattedTimestamp = formattedTimestamp;
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


	public WindowedDiscountByInstanceSocksPayload(String category, String discountApplied, long timestamp,
			String formattedTimestamp, String windowStart, String windowEnd) {
		super();
		this.category = category;
		this.discountApplied = discountApplied;
		this.timestamp = timestamp;
		this.formattedTimestamp = formattedTimestamp;
		this.windowStart = windowStart;
		this.windowEnd = windowEnd;
	}

	@Override
	public String toString() {
		return "WindowedDiscountByInstanceSocksPayload [category=" + category + ", discountApplied=" + discountApplied
				+ ", timestamp=" + timestamp + ", formattedTimestamp=" + formattedTimestamp + ", windowStart="
				+ windowStart + ", windowEnd=" + windowEnd + "]";
	}

}

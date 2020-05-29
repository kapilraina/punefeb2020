package com.ms.bootcamp.discountserviceprocessor.stream.processors;

import java.util.Date;

public class WindowedDiscountByInstance {
	private String category;
	private double discountApplied;
	private long timestamp;
	private Date windowStart;
	private Date windowEnd;

	@Override
	public String toString() {
		return "WindowedDiscountByInstance [category=" + category + ", discountApplied=" + discountApplied
				+ ", timestamp=" + timestamp + ", windowStart=" + windowStart + ", windowEnd=" + windowEnd + "]";
	}

	public WindowedDiscountByInstance() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WindowedDiscountByInstance(String category, double discountApplied, long timestamp, Date windowStart,
			Date windowEnd) {
		super();
		this.category = category;
		this.discountApplied = discountApplied;
		this.timestamp = timestamp;
		this.windowStart = windowStart;
		this.windowEnd = windowEnd;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getDiscountApplied() {
		return discountApplied;
	}

	public void setDiscountApplied(double discountApplied) {
		this.discountApplied = discountApplied;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Date getWindowStart() {
		return windowStart;
	}

	public void setWindowStart(Date windowStart) {
		this.windowStart = windowStart;
	}

	public Date getWindowEnd() {
		return windowEnd;
	}

	public void setWindowEnd(Date windowEnd) {
		this.windowEnd = windowEnd;
	}

}

package com.ms.bootcamp.discountserviceprocessor.stream.processors;

import java.util.Date;

public class AggregatedWindowedDiscount {

	private String category;
	private double windowTotal;
	private Date windowStart;
	private Date windowEnd;

	public AggregatedWindowedDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AggregatedWindowedDiscount(String category, double windowTotal, Date windowStart, Date windowEnd) {
		super();
		this.category = category;
		this.windowTotal = windowTotal;
		this.windowStart = windowStart;
		this.windowEnd = windowEnd;
	}

	public double getWindowTotal() {
		return windowTotal;
	}

	public void setWindowTotal(double windowTotal) {
		this.windowTotal = windowTotal;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	@Override
	public String toString() {
		return "AggregatedWindowedDiscount [category=" + category + ", windowTotal=" + windowTotal + ", windowStart="
				+ windowStart + ", windowEnd=" + windowEnd + "]";
	}

}

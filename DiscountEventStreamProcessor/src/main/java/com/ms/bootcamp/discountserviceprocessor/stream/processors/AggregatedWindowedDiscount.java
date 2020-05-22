package com.ms.bootcamp.discountserviceprocessor.stream.processors;

import java.util.Date;

public class AggregatedWindowedDiscount {

	private String category;
	private double windotTotal;
	private Date windowStart;
	private Date windowEnd;

	public AggregatedWindowedDiscount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AggregatedWindowedDiscount(String category, double windotTotal, Date windowStart, Date windowEnd) {
		super();
		this.category = category;
		this.windotTotal = windotTotal;
		this.windowStart = windowStart;
		this.windowEnd = windowEnd;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getWindotTotal() {
		return windotTotal;
	}

	public void setWindotTotal(double windotTotal) {
		this.windotTotal = windotTotal;
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

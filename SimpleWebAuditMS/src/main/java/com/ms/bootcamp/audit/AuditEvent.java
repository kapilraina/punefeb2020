package com.ms.bootcamp.audit;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AuditEvent {

	@Id
	private String id;
	private String payload;
	private Date timestamp;

	

	public AuditEvent(String id, String payload, Date timestamp) {
		super();
		this.id = id;
		this.payload = payload;
		this.timestamp = timestamp;
	}

	public AuditEvent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}

package com.platformtest.app.dto;

import java.io.Serializable;

import com.platformtest.app.domain.Asks;

public class AsksDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String userId;
	private String title;
	private String bodyAsk;
	
	public AsksDTO(Asks obj) {
		super();
		id = obj.getId();
		userId = obj.getUserId();
		title = obj.getTitle();
		bodyAsk = obj.getBodyAsk();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBodyAsk() {
		return bodyAsk;
	}

	public void setBodyAsk(String bodyAsk) {
		this.bodyAsk = bodyAsk;
	}
}

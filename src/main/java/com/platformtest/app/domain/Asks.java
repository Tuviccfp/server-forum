package com.platformtest.app.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Asks implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	private String userId;
	private String title;
	private String bodyAsk;
	
	@DBRef
	private User user;
	
	public Asks() {
		// TODO Auto-generated constructor stub
	}

	public Asks(String id, User user, String title, String bodyAsk) {
		super();
		this.id = id;
		userId = user.getId();
		this.title = title;
		this.bodyAsk = bodyAsk;
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

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asks other = (Asks) obj;
		return Objects.equals(id, other.id);
	}
}

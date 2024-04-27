package com.platformtest.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Asks implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
    private String title;
    private String bodyAsk;

	@DBRef
	private User user;
	@DBRef
	private List<Response> response;

	public Asks() {
		// TODO Auto-generated constructor stub
	}

	public Asks(String ided, String id, String title, String bodyAsk, User user) {
		super();
		this.id = id;
		this.title = title;
		this.bodyAsk = bodyAsk;
		this.user = user;
		this.response = new ArrayList<Response>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Response> getResponse() {
		return response;
	}

	public void setResponse(List<Response> response) {
		this.response = response;
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

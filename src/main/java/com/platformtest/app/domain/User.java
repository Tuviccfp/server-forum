package com.platformtest.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Document
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@NotNull
	private String email;
	@NotNull
	private String name;
	@NotNull
	private String password;
	private String role = "USER";
	
	@DBRef
	private List<Asks> asks;
	@DBRef
	private List<Response> response;
	@DBRef
	private List<Category> categories;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String id, String email, String password, String name) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.asks = new ArrayList<Asks>();
		this.response = new ArrayList<>();
		this.categories = new ArrayList<>();
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
		User other = (User) obj;
		return Objects.equals(id, other.id);
	}
	
}

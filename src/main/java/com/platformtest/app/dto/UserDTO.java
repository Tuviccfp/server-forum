package com.platformtest.app.dto;

import java.io.Serializable;
import java.util.Objects;

import com.platformtest.app.domain.User;

public class UserDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String email;
	private String password;
	private boolean terms;
	private boolean idadeAcimaDos18;
	private String role = "USER";
	
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public UserDTO(User obj) {
		super();
		id = obj.getId();
		email = obj.getEmail();
		password = obj.getPassword();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isTerms() {
		return terms;
	}

	public void setTerms(boolean terms) {
		this.terms = terms;
	}

	public boolean isIdadeAcimaDos18() {
		return idadeAcimaDos18;
	}

	public void setIdadeAcimaDos18(boolean idadeAcimaDos18) {
		this.idadeAcimaDos18 = idadeAcimaDos18;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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
		UserDTO other = (UserDTO) obj;
		return Objects.equals(id, other.id);
	}
}

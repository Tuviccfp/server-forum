package com.platformtest.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Document
public class Asks implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@NotNull
    private String title;
    @NotNull
	private String bodyAsk;

	@DBRef
	private User user;
	@DBRef
	private List<Response> response;

	public Asks(String id, String title, String bodyAsk, User user) {
		super();
		this.id = id;
		this.title = title;
		this.bodyAsk = bodyAsk;
		this.user = user;
		this.response = new ArrayList<Response>();
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

package com.platformtest.app.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Getter
@Setter
@Document
public class Response implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String response;

    @DBRef
    private User user;
    @DBRef
    private Asks asks;

    public Response(Asks asks, String id, User user, String response) {
        this.asks = asks;
        this.id = id;
        this.user = user;
        this.response = response;
    }

}

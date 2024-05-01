package com.platformtest.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document
public class Category {

    @Id
    private String id;
    @NotNull
    private String categoryName;

    @DBRef
    private List<Asks> asks;
    @DBRef
    @JsonIgnore
    private User user;

    public Category(String categoryName, String id, User user) {
        this.categoryName = categoryName;
        this.asks = new ArrayList<Asks>();
        this.id = id;
        this.user = user;
    }
}

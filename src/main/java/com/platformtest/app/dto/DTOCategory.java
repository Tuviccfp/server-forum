package com.platformtest.app.dto;

import com.platformtest.app.domain.Asks;
import com.platformtest.app.domain.User;

public record DTOCategory(String id, String categoryName, Asks asks, User user) {
}

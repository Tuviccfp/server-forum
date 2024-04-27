package com.platformtest.app.dto;

import com.platformtest.app.domain.Asks;
import com.platformtest.app.domain.User;

public record DTOResponse(String id, String response, Asks ask, User user) {
}

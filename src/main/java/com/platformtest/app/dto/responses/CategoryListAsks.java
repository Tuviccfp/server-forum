package com.platformtest.app.dto.responses;

import com.platformtest.app.domain.Asks;

import java.util.List;

public record CategoryListAsks(String id, String categoryName, List<Asks> ask) {
}

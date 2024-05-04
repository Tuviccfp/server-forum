package com.platformtest.app.dto.responses;

import com.platformtest.app.domain.Asks;
import com.platformtest.app.domain.Category;

import java.util.List;

public record DataExtraProfile(List<Asks> asksList, List<Category> categoriesList) {
}

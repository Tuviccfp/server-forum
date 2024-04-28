package com.platformtest.app.controller.interfaces;

import java.util.List;

import com.platformtest.app.dto.DTOAsks;
import com.platformtest.app.dto.responses.AskListUserName;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.platformtest.app.domain.Asks;

public interface MethodsAsksController {
	ResponseEntity<List<AskListUserName>> findAll();
	ResponseEntity<DTOAsks> findById(@PathVariable String id);
	ResponseEntity<String> insertNewAsks(@RequestBody DTOAsks asks, @AuthenticationPrincipal Jwt jwt);
	ResponseEntity<String> updateAsks(@RequestBody DTOAsks asks, @PathVariable String id);
	ResponseEntity<String> deleteAsks(@PathVariable String id);
}

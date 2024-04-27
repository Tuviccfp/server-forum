package com.platformtest.app.controller.interfaces;

import com.platformtest.app.domain.Response;
import com.platformtest.app.dto.DTOResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface MethodsResponseController {
    ResponseEntity<List<Response>> listAll();
    ResponseEntity<DTOResponse> getById(@PathVariable String id);
    ResponseEntity<String> insertNewResponse(@RequestBody DTOResponse dtoResponse, @AuthenticationPrincipal Jwt jwt, @PathVariable String id);
    ResponseEntity<String> updateResponse(@RequestBody DTOResponse dtoResponse, @PathVariable String id);
    ResponseEntity<String> deleteResponse(@PathVariable String id);
}

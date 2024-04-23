package com.platformtest.app.controller.interfaces;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.platformtest.app.domain.Asks;
import com.platformtest.app.dto.AsksDTO;

public interface MethodsAsksController {
	ResponseEntity<List<Asks>> findAll();
	ResponseEntity<AsksDTO> findById(@PathVariable String id);
	ResponseEntity<String> insertNewAsks(@RequestBody AsksDTO askDTO);
}

package com.platformtest.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.platformtest.app.controller.interfaces.MethodsAsksController;
import com.platformtest.app.domain.Asks;
import com.platformtest.app.dto.AsksDTO;

public class AsksController implements MethodsAsksController {

	@Override
	public ResponseEntity<List<Asks>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<AsksDTO> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> insertNewAsks(AsksDTO askDTO) {
		// TODO Auto-generated method stub
		return null;
	}


}

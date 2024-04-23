package com.platformtest.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platformtest.app.domain.Asks;
import com.platformtest.app.exception.FieldsEmpty;
import com.platformtest.app.exception.IdNotFound;
import com.platformtest.app.repository.AsksRepository;

@Service
public class AsksService {

	@Autowired
	private AsksRepository repoAsk;
	
	public List<Asks> findAll() {
		List<Asks> listAllAsk = repoAsk.findAll();
		return listAllAsk;
	}
	
	public Optional<Asks> findById(String id) {
		if (!id.isEmpty()) {
			Optional<Asks> existAsk = repoAsk.findById(id);
			if (!existAsk.isPresent()) {
				throw new IdNotFound("Não foi possível localizar nenhum id");
			}
			return existAsk;
		} else {
			throw new FieldsEmpty("Não existe nenhum identificador passado por Request Params.");
		}
	}
	
	public Asks insertNewAsk(Asks ask) {
		return repoAsk.insert(ask);
	}
}

package com.platformtest.app.services;

import com.platformtest.app.domain.Response;
import com.platformtest.app.repository.ResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;

    public ResponseService(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }
    public List<Response> findAll() {
        return responseRepository.findAll();
    }
    public Response findById(String id) {
        return responseRepository.findById(id).orElse(null);
    }
    public void delete(String id) {
        responseRepository.deleteById(id);
    }
    public void save(Response response) {
        responseRepository.save(response);
    }
    public Response insertNewResponse(Response response) {
        return responseRepository.insert(response);
    }
}

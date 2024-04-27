package com.platformtest.app.controller;

import com.platformtest.app.controller.interfaces.MethodsResponseController;
import com.platformtest.app.domain.Asks;
import com.platformtest.app.domain.Response;
import com.platformtest.app.domain.User;
import com.platformtest.app.dto.DTOResponse;
import com.platformtest.app.exception.IdNotFound;
import com.platformtest.app.repository.AsksRepository;
import com.platformtest.app.repository.ResponseRepository;
import com.platformtest.app.repository.UserRepository;
import com.platformtest.app.services.AsksService;
import com.platformtest.app.services.ResponseService;
import com.platformtest.app.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/responses")
public class ResponseController implements MethodsResponseController {

    private final ResponseService responseService;
    private final ResponseRepository responseRepository;
    private final AsksService asksService;
    private final UserServices userServices;
    private final UserRepository userRepository;
    private final AsksRepository asksRepository;

    public ResponseController(ResponseService responseService, ResponseRepository responseRepository, AsksService asksService, UserServices userServices, UserRepository userRepository, AsksRepository asksRepository) {
        this.responseService = responseService;
        this.responseRepository = responseRepository;
        this.asksService = asksService;
        this.userServices = userServices;
        this.userRepository = userRepository;
        this.asksRepository = asksRepository;
    }

    @Override
    public ResponseEntity<List<Response>> listAll() {
        return null;
    }

    @Override
    public ResponseEntity<DTOResponse> getById(String id) {
        Response response = responseService.findById(id);
        if (response == null) {
            throw new IdNotFound("Não existe nada com esse id");
        }
        DTOResponse dtoResponse = new DTOResponse(response.getId(), response.getResponse(), response.getAsks(), response.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(dtoResponse);
    }

    @PostMapping(value = "/new-response/{id}")
    @Override
    public ResponseEntity<String> insertNewResponse(DTOResponse dtoResponse, Jwt jwt, String id) {
        String idReqHeader = jwt.getSubject();
        var user = userServices.findById(idReqHeader);
        var asks = asksService.findById(id);
        if (user.isEmpty() && asks.isEmpty()) {
            throw new IdNotFound("Não existe nada com esse id");
        }
        Asks instanceAsk = asks.get();
        User instanceUser = user.get();
        Response newResponse = new Response(instanceAsk, dtoResponse.id(), instanceUser, dtoResponse.response());
        newResponse = responseService.insertNewResponse(newResponse);
        instanceUser.getResponse().add(newResponse);
        userRepository.save(instanceUser);
        instanceAsk.getResponse().add(newResponse);
        asksRepository.save(instanceAsk);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    @PutMapping(value = "/update/{id}")
    @Override
    public ResponseEntity<String> updateResponse(DTOResponse dtoResponse, String id) {
        var existResponse = responseService.findById(id);
        if (existResponse == null) {
            throw new IdNotFound("Não existe nada com esse id");
        }
        existResponse.setResponse(dtoResponse.response());
        responseService.save(existResponse);
        return ResponseEntity.status(HttpStatus.OK).body("Resposta publicada com sucesso.");
    }

    @Override
    public ResponseEntity<String> deleteResponse(String id) {
        responseService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Resposta publicada");
    }
}

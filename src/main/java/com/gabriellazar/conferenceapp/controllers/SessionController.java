package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Session;
import com.gabriellazar.conferenceapp.repositories.SessionRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;


@RestController
@RequestMapping("/api/v1/session")
public class SessionController {

    private SessionRepository sessionRepository;

    public SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    @ApiOperation(value = "Get all the sessions", notes = "Get all the sessions")
    public ResponseEntity<List<Session>> getAllSessions(){
        return ResponseEntity.status(HttpStatus.OK).body(sessionRepository.findAll());
    }


}

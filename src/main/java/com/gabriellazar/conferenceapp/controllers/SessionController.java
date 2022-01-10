package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Session;
import com.gabriellazar.conferenceapp.services.SessionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    @ApiOperation(value = "Get all the sessions", notes = "Get all the sessions")
    @PreAuthorize("hasAnyAuthority('ADMIN','ATTENDEE')")
    public ResponseEntity<List<Session>> getAllSessions() {
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getAllSessions());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Session by Id", notes = "Get Session By Id")
    @PreAuthorize("hasAnyAuthority('ADMIN','ATTENDEE')")
    public ResponseEntity<Session> getSessionById(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getSessionById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a session",notes = "Create a session")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Session> createSession(@RequestBody @Valid final Session session) {

        Session createdSession = sessionService.createSession(session);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createdSession);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete Session By id",notes = "Delete Session by Id")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteSession(@PathVariable(name = "id") final Long id){
        sessionService.deleteSessionById(id);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).body("SUCCESS");
    }

    @PutMapping("{id}")
    @ApiOperation(value = "Edit a session",notes = "Edit a session")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Session> editSession(@PathVariable(name = "id") Long id, @RequestBody @Valid final Session session){
       Session editSession = sessionService.editSessionById(id,session);
        return ResponseEntity.status(HttpStatus.OK).body(editSession);
    }


}

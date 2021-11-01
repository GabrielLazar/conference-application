package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Attendee;
import com.gabriellazar.conferenceapp.models.LoginAttendee;
import com.gabriellazar.conferenceapp.security.JWTUtil;
import com.gabriellazar.conferenceapp.services.AttendeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendee")
public class AttendeeController {

    private AttendeeService attendeeService;
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public AttendeeController(AttendeeService attendeeService, AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.attendeeService = attendeeService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    @RequestMapping("/authenticate")
    public ResponseEntity<String> generateToken(@RequestBody LoginAttendee loginAttendee){
        String email = loginAttendee.getEmail();
        String password = loginAttendee.getPassword();
        Attendee attendee = attendeeService.getAttendeeByEmail(email);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,password);
        authenticationManager.authenticate(authenticationToken);
        String token = jwtUtil.generateToken(attendee,email);
        return ResponseEntity.ok(token);
    }
}


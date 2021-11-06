package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.services.SpeakerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;



@RestController
@RequestMapping("/api/v1/speakers")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpeakerController {

    private SpeakerService speakerService;

    @Autowired
    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Speakers", notes = "Get all Speakers and query them by company and title")
    @PreAuthorize("hasAnyAuthority('ADMIN','ATTENDEE')")
    public ResponseEntity<List<Speaker>> getSpeakers(
            @RequestParam("company") final Optional<String> companyName,
            @RequestParam("title") final Optional<String> title) {
        return ResponseEntity.status(HttpStatus.OK).body(speakerService.getAllSpeakers(companyName, title));
    }

    @GetMapping("/{speakerId}")
    @ApiOperation(notes = "Get a Speaker by Id", value = "Get a Speaker by Id")
    @PreAuthorize("hasAnyAuthority('ADMIN','ATTENDEE')")
    public ResponseEntity<Speaker> getSpeakersById(@PathVariable(name = "speakerId") final Long speakerId) {

        return ResponseEntity.status(HttpStatus.OK).body(speakerService.getSpeakerById(speakerId));
    }

    @PostMapping
    @ApiOperation(value = "Create a new Speaker", notes = "Create a new Speaker")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Speaker> createSpeaker(@RequestBody @Valid final Speaker speaker) {

        Speaker speakerCreated = speakerService.createSpeaker(speaker);
        return ResponseEntity.status(HttpStatus.OK).body(speakerCreated);
    }

    @DeleteMapping("/{speakerId}")
    @ApiOperation(value = "Delete a Speaker by Id", notes = "Delete a Speaker by Id")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteSpeaker(@PathVariable(name = "speakerId") final Long speakerId) {
        speakerService.deleteSpeakerById(speakerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("SUCCESS");
    }

    @PutMapping("/{speakerId}")
    @ApiOperation(value = "Update an existing Speaker", notes = "Update an existing Speaker")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Speaker> updateSpeaker(
            @PathVariable(name = "speakerId") final Long id,
            @RequestBody @Valid @NotNull final Speaker speaker) {

        Speaker existingSpeaker = speakerService.editSpeakerById(id, speaker);
        return ResponseEntity.status(HttpStatus.OK).body(existingSpeaker);
    }

}

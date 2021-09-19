package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.services.SpeakerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

    private SpeakerService speakerService;

    @Autowired
    public SpeakerController(SpeakerService speakerService) {
        this.speakerService = speakerService;
    }

    @GetMapping
    @ApiOperation(value = "Get all Speakers", notes = "Get all Speakers")
    public ResponseEntity<List<Speaker>> getSpeakers(@RequestParam("company") final Optional<String> companyName) {
        return ResponseEntity.status(HttpStatus.OK).body(speakerService.getAllSpeakers(companyName));
    }

    @GetMapping("/{speakerId}")
    @ApiOperation(notes = "Get a Speaker by Id", value = "Get a Speaker by Id")
    public ResponseEntity<Speaker> getSpeakersById(@PathVariable(name = "speakerId") final Long speakerId) {

        return ResponseEntity.status(HttpStatus.OK).body(speakerService.getSpeakerById(speakerId));
    }

    @PostMapping
    @ApiOperation(value = "Create a new Speaker", notes = "Create a new Speaker")
    public ResponseEntity<Speaker> createSpeaker(@RequestBody @Valid final Speaker speaker) {

        Speaker speakerCreated = speakerService.createSpeaker(speaker);
        return ResponseEntity.status(HttpStatus.OK).body(speakerCreated);
    }

    @DeleteMapping("/{speakerId}")
    @ApiOperation(value = "Delete a Speaker by Id", notes = "Delete a Speaker by Id")
    public ResponseEntity<String> deleteSpeaker(@PathVariable(name = "speakerId") final Long speakerId) {
        speakerService.deleteSpeakerById(speakerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("SUCCESS");
    }

    @PutMapping("/{speakerId}")
    @ApiOperation(value = "Update an existing Speaker", notes = "Update an existing Speaker")
    public ResponseEntity<Speaker> updateSpeaker(
            @PathVariable(name = "speakerId") final Long id,
            @RequestBody @Valid @NotNull final Speaker speaker) {

        Speaker existingSpeaker = speakerService.editSpeakerById(id, speaker);
        return ResponseEntity.status(HttpStatus.OK).body(existingSpeaker);
    }

//    @GetMapping("/company/{companyName}")
//    @ApiOperation(value = "Get all the speakers by company", notes = "Get all the speakers by company")
//    public ResponseEntity<List<Speaker>> getSpeakersByCompany(@PathVariable(name = "companyName") final String companyName) {
//        return ResponseEntity.status(HttpStatus.OK).body(speakerService.getSpeakerByCompany(companyName));
//    }


}

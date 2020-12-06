package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.ApiError;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.repositories.SpeakerRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;


    @GetMapping
    @ApiOperation(value = "Get all Speakers", notes = "Get all Speakers")
    public ResponseEntity<List<Speaker>> getSpeakers() {
        return ResponseEntity.status(HttpStatus.OK).body(speakerRepository.findAll());
    }

    @GetMapping("/{speakerId}")
    @ApiOperation(notes = "Get a Speaker by Id", value = "Get a Speaker by Id")
    public ResponseEntity<Speaker> getSpeakersById(@PathVariable(name = "speakerId") final Long speakerId) {

        Speaker speaker = speakerRepository.findById(speakerId).orElseThrow(
                () -> new DataNotFoundException("Speaker not found with this id :: " + speakerId));
        return ResponseEntity.status(HttpStatus.OK).body(speaker);

    }

    @PostMapping
    @ApiOperation(value = "Create a new Speaker", notes = "Create a new Speaker")
    public ResponseEntity<Speaker> createSpeaker(@RequestBody @Valid Speaker speaker){

      Speaker speakerCreated =  speakerRepository.saveAndFlush(speaker);
      return ResponseEntity.status(HttpStatus.OK).body(speakerCreated);
    }

    @DeleteMapping("/{speakerId}")
    @ApiOperation(value = "Delete a Speaker by Id", notes = "Delete a Speaker by Id")
    public ResponseEntity<String> deleteSpeaker(@PathVariable(name = "speakerId") final Long speakerId){
        speakerRepository.deleteById(speakerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("SUCCESS");
    }


}

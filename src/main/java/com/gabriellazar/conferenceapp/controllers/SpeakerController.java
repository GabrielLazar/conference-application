package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.ApiError;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.repositories.SpeakerRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {


    private SpeakerRepository speakerRepository;

    @Autowired
    public SpeakerController(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

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
    public ResponseEntity<Speaker> createSpeaker(@RequestBody @Valid final Speaker speaker){

      Speaker speakerCreated =  speakerRepository.saveAndFlush(speaker);
      return ResponseEntity.status(HttpStatus.OK).body(speakerCreated);
    }

    @DeleteMapping("/{speakerId}")
    @ApiOperation(value = "Delete a Speaker by Id", notes = "Delete a Speaker by Id")
    public ResponseEntity<String> deleteSpeaker(@PathVariable(name = "speakerId") final Long speakerId){
        speakerRepository.deleteById(speakerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("SUCCESS");
    }

    @PutMapping("/{speakerId}")
    @ApiOperation(value = "Update an existing Speaker",notes = "Update an existing Speaker")
    public ResponseEntity<Speaker> updateSpeaker(
            @PathVariable(name = "speakerId") final Long id,
            @RequestBody @Valid @NotNull final Speaker speaker){

        Speaker existingSpeaker = speakerRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Speaker not found with this id :: " + id));
        BeanUtils.copyProperties(speaker,existingSpeaker,"speaker_id");
        speakerRepository.saveAndFlush(existingSpeaker);
        return ResponseEntity.status(HttpStatus.OK).body(existingSpeaker);
    }

    @GetMapping("/company/{companyName}")
    @ApiOperation(value = "Get all the speakers by company",notes = "Get all the speakers by company")
    public ResponseEntity<List<Speaker>> getSpeakersByCompany(@PathVariable(name = "companyName") final String companyName){
        return  ResponseEntity.status(HttpStatus.OK).body(speakerRepository.findAllByCompany(companyName));
    }



}

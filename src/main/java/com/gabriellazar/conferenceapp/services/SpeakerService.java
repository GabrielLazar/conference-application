package com.gabriellazar.conferenceapp.services;

import com.gabriellazar.conferenceapp.models.Speaker;

import java.util.List;
import java.util.Optional;

public interface SpeakerService {

    List<Speaker> getAllSpeakers(Optional<String> companyName,Optional<String> jobTitle);
    Speaker getSpeakerById(Long id);
    Speaker createSpeaker(Speaker speaker);
    Speaker editSpeakerById(Long id,Speaker speaker);
    void deleteSpeakerById(Long id);


}

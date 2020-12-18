package com.gabriellazar.conferenceapp.services;

import com.gabriellazar.conferenceapp.models.Speaker;

import java.util.List;

public interface SpeakerService {

    List<Speaker> getAllSpeakers();
    Speaker getSpeakerById(Long id);
    Speaker createSpeaker(Speaker speaker);
    Speaker editSpeakerById(Long id,Speaker speaker);
    void deleteSpeakerById(Long id);
    List<Speaker> getSpeakerByCompany(String company);

}

package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.repositories.SpeakerRepository;
import com.gabriellazar.conferenceapp.services.SpeakerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository speakerRepository;

    @Autowired
    public SpeakerServiceImpl(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    @Override
    public List<Speaker> getAllSpeakers() {
        return speakerRepository.findAll();
    }

    @Override
    public Speaker getSpeakerById(Long id) {
        return speakerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Speaker not found with this id :: " +  id));
    }

    @Override
    public Speaker createSpeaker(Speaker speaker) {

     return speakerRepository.saveAndFlush(speaker);
    }

    @Override
    public Speaker editSpeakerById(Long id,Speaker speaker) {

        Speaker existingSpeaker = getSpeakerById(id);
        BeanUtils.copyProperties(speaker, existingSpeaker,"speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

    @Override
    public void deleteSpeakerById(Long id) {
        speakerRepository.deleteById(id);
    }

    @Override
    public List<Speaker> getSpeakerByCompany(String company) {
        return speakerRepository.findAllByCompany(company);
    }
}

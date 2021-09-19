package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.repositories.SpeakerRepository;
import com.gabriellazar.conferenceapp.services.SpeakerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SpeakerServiceImpl implements SpeakerService {

    private SpeakerRepository speakerRepository;

    @Autowired
    public SpeakerServiceImpl(SpeakerRepository speakerRepository) {
        this.speakerRepository = speakerRepository;
    }

    @Override
    public List<Speaker> getAllSpeakers(final Optional<String> companyName) {
        List<Speaker> speakers = null;
        try {
            speakers = speakerRepository.findAll();
            if (companyName.isPresent()) {
                speakers = speakers.stream()
                        .filter(s -> s.getCompany().equalsIgnoreCase(companyName.get()))
                        .collect(Collectors.toList());
            }
            log.info("Successfully getting all speakers :: {}", speakers);
        } catch (Exception e) {
            log.error("Exception in getting all speakers :: Exception {}", e);
            return Collections.emptyList();
        }
        return speakers;
    }

    @Override
    public Speaker getSpeakerById(Long id) {
        return speakerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Speaker not found with this id :: " + id));
    }

    @Override
    public Speaker createSpeaker(Speaker speaker) {
        Speaker savedSpeaker = null;
        try {
            savedSpeaker = speakerRepository.saveAndFlush(speaker);
            log.info("Successfully saved speaker :: {}", savedSpeaker);
        } catch (Exception e) {
            log.error("Exception in saving speaker :: {}, Exception :: {}", speaker, e);
        }
        return getSpeakerById(savedSpeaker.getSpeaker_id());
    }

    @Override
    public Speaker editSpeakerById(Long id, Speaker speaker) {
        try {
            Speaker existingSpeaker = getSpeakerById(id);
            BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
            speakerRepository.saveAndFlush(existingSpeaker);
            log.info("Updating speaker with id :: {} Speaker :: {}", id, speaker);
        } catch (Exception e) {
            log.error("Exception in updating speaker id :: {} Speaker :: {} Exception", id, speaker, e);
        }
        return getSpeakerById(id);
    }

    @Override
    public void deleteSpeakerById(Long id) {
        try {
            speakerRepository.deleteById(id);
            log.info("Successfully deleted speaker with id :: {}", id);
        } catch (Exception e) {
            log.error("Exception in deleting speaker with id :: {}", id);
        }

    }

}

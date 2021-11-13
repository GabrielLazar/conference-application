package com.gabriellazar.conferenceapp.services.impl;


import com.gabriellazar.conferenceapp.models.Attendee;
import com.gabriellazar.conferenceapp.repositories.AttendeeRepository;
import com.gabriellazar.conferenceapp.services.AttendeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AttendeeServiceImpl implements AttendeeService {

    private AttendeeRepository attendeeRepository;

    public AttendeeServiceImpl(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    @Override
    public Attendee getAttendeeByEmail(String email) {
        return attendeeRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Attendee saveAttendee(Attendee attendee) {
        Attendee savedAttendee = null;
        try{
            savedAttendee = attendeeRepository.save(attendee);
            log.info("Successfully saved attendee :: {}", savedAttendee);
        } catch (Exception e){
            log.error("Exception in saving attendee :: {}, Exception :: {}", attendee, e);
        }
        return getAttendeeByEmail(savedAttendee.getEmail());
    }
}

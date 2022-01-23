package com.gabriellazar.conferenceapp.services.impl;


import com.gabriellazar.conferenceapp.models.Attendee;
import com.gabriellazar.conferenceapp.repositories.AttendeeRepository;
import com.gabriellazar.conferenceapp.services.AttendeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendeeServiceImpl implements AttendeeService {

    private AttendeeRepository attendeeRepository;
    private BCryptPasswordEncoder encoder;

    public AttendeeServiceImpl(AttendeeRepository attendeeRepository, @Lazy BCryptPasswordEncoder encoder) {
        this.attendeeRepository = attendeeRepository;
        this.encoder = encoder;
    }

    @Override
    public List<Attendee> getAllAttendees(Optional<String> role) {
        List<Attendee> attendees = null;
        try {
            attendees = attendeeRepository.findAll();
            if(role.isPresent()){
                attendees = attendees
                        .stream()
                        .filter(attendee -> attendee.getRole().equalsIgnoreCase(role.get()))
                        .collect(Collectors.toList());
            }
            log.info("Successfully getting attendees :: {}");
        } catch (Exception e){
            log.error("Exception in getting attendees:: Exception {}", e);
            return Collections.emptyList();
        }
        return attendees;
    }

    @Override
    public Attendee getAttendeeByEmail(String email) {
        return attendeeRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Attendee saveAttendee(Attendee attendee) {
        Attendee savedAttendee = null;
        try {
            attendee.setRole("ATTENDEE");
            attendee.setPassword(encoder.encode(attendee.getPassword()));
            savedAttendee = attendeeRepository.save(attendee);
            log.info("Successfully saved attendee :: {}", savedAttendee);
        } catch (Exception e) {
            log.error("Exception in saving attendee :: {}, Exception :: {}", attendee, e);
        }
        return getAttendeeByEmail(savedAttendee.getEmail());
    }

    @Override
    public String updateToAdmin(Attendee attendee) {
        try {
            attendee.setRole("ADMIN");
            attendeeRepository.save(attendee);
            log.info("Successfully save admin :: {}", attendee);
        } catch (Exception e) {
            log.error("Exception in saving attendee :: {}, Exception :: {}", attendee, e);
            return "UNSUCCESSFULLY UPDATING TO ADMIN :: " + attendee.getEmail();
        }
        return "SUCCESSFULLY UPDATING TO ADMIN :: " + attendee.getEmail();
    }


}

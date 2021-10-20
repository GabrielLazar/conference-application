package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Attendee;
import com.gabriellazar.conferenceapp.repositories.AttendeeRepository;
import com.gabriellazar.conferenceapp.services.AttendeeService;

public class AttendeeServiceImpl implements AttendeeService {

    private AttendeeRepository attendeeRepository;

    public AttendeeServiceImpl(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    @Override
    public Attendee getAttendeeByEmail(String email) {
        return attendeeRepository.findByEmail(email).orElse(null);
    }
}

package com.gabriellazar.conferenceapp.services;

import com.gabriellazar.conferenceapp.models.Attendee;

public interface AttendeeService {

    Attendee getAttendeeByEmail(String email);
    Attendee saveAttendee(Attendee attendee);
}

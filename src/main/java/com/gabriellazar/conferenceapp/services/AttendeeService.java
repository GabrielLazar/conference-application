package com.gabriellazar.conferenceapp.services;

import com.gabriellazar.conferenceapp.models.Attendee;

import java.util.List;
import java.util.Optional;

public interface AttendeeService {

    List<Attendee> getAllAttendees(Optional<String> role);
    Attendee getAttendeeByEmail(String email);
    Attendee saveAttendee(Attendee attendee);
    String updateToAdmin(Attendee attendee);
}

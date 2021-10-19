package com.gabriellazar.conferenceapp.repositories;

import com.gabriellazar.conferenceapp.models.Attendee;
import org.springframework.data.repository.CrudRepository;

public interface AttendeeRepository extends CrudRepository<Attendee,Long> {
}

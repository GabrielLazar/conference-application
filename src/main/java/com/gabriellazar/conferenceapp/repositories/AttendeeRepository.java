package com.gabriellazar.conferenceapp.repositories;

import com.gabriellazar.conferenceapp.models.Attendee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AttendeeRepository extends CrudRepository<Attendee,Long> {

    Optional<Attendee> findByEmail(String email);
}

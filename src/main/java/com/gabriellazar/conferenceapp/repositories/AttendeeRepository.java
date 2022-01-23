package com.gabriellazar.conferenceapp.repositories;

import com.gabriellazar.conferenceapp.models.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AttendeeRepository extends JpaRepository<Attendee,Long> {

    Optional<Attendee> findByEmail(String email);
}

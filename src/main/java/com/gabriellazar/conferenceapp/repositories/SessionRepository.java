package com.gabriellazar.conferenceapp.repositories;

import com.gabriellazar.conferenceapp.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {
}

package com.gabriellazar.conferenceapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<com.gabriel.conferencedemo.models.Session,Long> {
}

package com.gabriellazar.conferenceapp.repositories;

import com.gabriellazar.conferenceapp.models.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkshopRepository extends JpaRepository<Workshop,Long> {

    List<Workshop> findByRoom(String room);
}

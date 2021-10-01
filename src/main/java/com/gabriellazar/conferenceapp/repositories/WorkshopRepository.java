package com.gabriellazar.conferenceapp.repositories;

import com.gabriellazar.conferenceapp.models.Workshop;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WorkshopRepository extends JpaRepository<Workshop,Long> {

}

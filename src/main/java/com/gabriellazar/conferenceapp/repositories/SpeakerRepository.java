package com.gabriellazar.conferenceapp.repositories;

import com.gabriellazar.conferenceapp.models.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SpeakerRepository extends JpaRepository<Speaker,Long> {

    List<Speaker> findAllByCompany(String company);

}

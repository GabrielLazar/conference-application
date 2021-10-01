package com.gabriellazar.conferenceapp.services;

import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.models.Workshop;

import java.util.List;
import java.util.Optional;

public interface WorkshopService {

    List<Workshop> getAllWorkshop(Optional<String> room);
    Workshop getWorkshopById(Long id);
    Workshop createWorkshop(Workshop workshop);
    Workshop editWorkshopById(Long id,Workshop workshop);
    void deleteWorkshopById(Long id);

}

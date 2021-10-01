package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Workshop;
import com.gabriellazar.conferenceapp.repositories.WorkshopRepository;
import com.gabriellazar.conferenceapp.services.WorkshopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WorkshopServiceImpl implements WorkshopService {

    private WorkshopRepository workshopRepository;

    @Autowired
    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public List<Workshop> getAllWorkshop(Optional<String> room) {
        List<Workshop> workshops = null;
        try {
            workshops = workshopRepository.findAll();
            if (room.isPresent()) {
                workshops = workshops.stream().filter(w -> w.getRoom().equalsIgnoreCase(room))
                        .collect(Collectors.toList());
            }
            log.info("Getting all workshops :: {}", workshops);
        } catch (Exception e) {
            log.error("Exception is getting all sessions :: {}", e);
            return Collections.emptyList();
        }
        return workshops;
    }

    @Override
    public Workshop getWorkshopById(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Workshop not found with this id :: " + id));
    }

    @Override
    public Workshop createWorkshop(Workshop workshop) {
        Workshop savedWorkshop = null;
        try {
            savedWorkshop = workshopRepository.saveAndFlush(workshop);
            log.info("Successfully saving workshop :: {}", savedWorkshop);
        } catch (Exception e) {
            log.error("Exception in saving workshop :: {} Exception ::{}", workshop, e);
        }
        return getWorkshopById(savedWorkshop.getWorkshop_id());
    }

    @Override
    public Workshop editWorkshopById(Long id, Workshop workshop) {
        try {
            Workshop existingWorkshop = getWorkshopById(id);
            BeanUtils.copyProperties(workshop, existingWorkshop, "workshop_id");
            Workshop savedWorkshop = workshopRepository.saveAndFlush(existingWorkshop);
            log.info("Updating workshop with id ::{} Workshop :: {}", id, savedWorkshop);
        } catch (Exception e) {
            log.error("Exception in updating workshop id ::{}, workshop ::{}, Exception ::{}", id, workshop, e);
        }
        return getWorkshopById(id);
    }

    @Override
    public void deleteWorkshopById(Long id) {
        try {
            workshopRepository.deleteById(id);
            log.info("Successfully deleted Workshop with id ::{}", id);
        } catch (Exception e) {
            log.error("Exception in deleting Workshop with id :: {}", id);
        }

    }

}

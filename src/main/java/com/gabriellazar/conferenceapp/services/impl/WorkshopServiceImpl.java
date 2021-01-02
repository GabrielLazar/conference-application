package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Workshop;
import com.gabriellazar.conferenceapp.repositories.WorkshopRepository;
import com.gabriellazar.conferenceapp.services.WorkshopService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkshopServiceImpl implements WorkshopService {

    private WorkshopRepository workshopRepository;

    @Autowired
    public WorkshopServiceImpl(WorkshopRepository workshopRepository) {
        this.workshopRepository = workshopRepository;
    }

    @Override
    public List<Workshop> getAllWorkshop() {
        return workshopRepository.findAll();
    }

    @Override
    public Workshop getWorkshopById(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Workshop not found with this id :: " + id));
    }

    @Override
    public Workshop createWorkshop(Workshop workshop) {
        return workshopRepository.saveAndFlush(workshop);
    }

    @Override
    public Workshop editWorkshopById(Long id, Workshop workshop) {
        Workshop existingWorkshop = getWorkshopById(id);
        BeanUtils.copyProperties(workshop, existingWorkshop, "workshop_id");
        return workshopRepository.saveAndFlush(existingWorkshop);
    }

    @Override
    public void deleteWorkshopById(Long id) {
        workshopRepository.deleteById(id);
    }

    @Override
    public List<Workshop> getWorkshopByRoom(String room) {
      return workshopRepository.findByRoom(room);
    }
}

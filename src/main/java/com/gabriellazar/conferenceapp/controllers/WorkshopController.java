package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Workshop;
import com.gabriellazar.conferenceapp.services.WorkshopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/workshops")
public class WorkshopController {

    private WorkshopService workshopService;

    @Autowired
    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @GetMapping("/")
    @ApiOperation(notes = "Get all workshops",value = "Get all workshops")
    public ResponseEntity<List<Workshop>> getAllWorkshop(){
        return ResponseEntity.status(200).body(workshopService.getAllWorkshop());
    }
}

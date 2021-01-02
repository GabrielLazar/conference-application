package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Workshop;
import com.gabriellazar.conferenceapp.services.WorkshopService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.OK).body(workshopService.getAllWorkshop());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Workshop by Id", notes = "Get Workshop by Id")
    public ResponseEntity<Workshop> getWorkshopById(@PathVariable(name = "id") final Long id){
        return ResponseEntity.status(HttpStatus.OK).body(workshopService.getWorkshopById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a Workshop", notes = "Create a Workshop")
    public ResponseEntity<Workshop> createWorkshop(@RequestBody final Workshop workshop){
        return ResponseEntity.status(HttpStatus.CREATED).body(workshopService.createWorkshop(workshop));
    }
}

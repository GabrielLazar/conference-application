package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Workshop;
import com.gabriellazar.conferenceapp.services.WorkshopService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/workshops")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WorkshopController {

    private WorkshopService workshopService;

    @Autowired
    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @GetMapping
    @ApiOperation(notes = "Get all workshops or filter workshops by room",
            value = "Get all workshops or get all workshops by room")
    @PreAuthorize("hasAnyAuthority('ADMIN','ATTENDEE')")
    public ResponseEntity<List<Workshop>> getAllWorkshop(
            @RequestParam(required = false, name = "room") final Optional<String> room) {
        List<Workshop> workshopList = workshopService.getAllWorkshop(room);
        return ResponseEntity.status(HttpStatus.OK).body(workshopList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get Workshop by Id", notes = "Get Workshop by Id")
    @PreAuthorize("hasAnyAuthority('ADMIN','ATTENDEE')")
    public ResponseEntity<Workshop> getWorkshopById(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(workshopService.getWorkshopById(id));
    }

    @PostMapping
    @ApiOperation(value = "Create a Workshop", notes = "Create a Workshop")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Workshop> createWorkshop(@RequestBody final Workshop workshop) {
        return ResponseEntity.status(HttpStatus.CREATED).body(workshopService.createWorkshop(workshop));
    }

    @PutMapping("/id")
    @ApiOperation(value = "Update Workshop", notes = "Update Workshop")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Workshop> updateWorkshop(@PathVariable(name = "id") final Long id,
                                                   @RequestBody @Valid @NotNull final Workshop workshop) {
        Workshop savedWorkshop = workshopService.editWorkshopById(id, workshop);
        return ResponseEntity.status(HttpStatus.OK).body(savedWorkshop);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a Workshop", notes = "Delete a Workshop")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteWorkshop(@PathVariable(name = "id") final Long id) {
        workshopService.deleteWorkshopById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("SUCCESS");
    }

}

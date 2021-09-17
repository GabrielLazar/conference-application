package com.gabriellazar.conferenceapp.controllers;

import com.gabriellazar.conferenceapp.models.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/info")
@Slf4j
public class InfoController {


    private Info info;

    @Autowired
    public InfoController(Info info) {
        this.info = info;
    }

    @GetMapping()
    public ResponseEntity<Info> getInfo(){
        log.info("Info :: {}",info);
        return ResponseEntity.status(HttpStatus.OK).body(info);
    }


}

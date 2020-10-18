package com.gabriellazar.conferenceapp.models;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Component
public class Info {

    @Value("${app.name}")
    private String applicationName;

    private String timeStamp;

    @Value("${app.version}")
    private String applicationVersion;

    public Info(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timeStamp = LocalDateTime.now().format(dateTimeFormatter);
    }


}

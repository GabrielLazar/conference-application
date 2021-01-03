package com.gabriellazar.conferenceapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity(name = "speakers")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Speaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long speaker_id;
    @NotBlank(message = "First Name cannot be blank")
    @NotNull
    private String first_name;
    @NotBlank(message = "Last Name cannot be blank")
    @NotNull
    private String last_name;
    @NotNull
    private String title;
    @NotNull
    private String company;
    @NotNull
    private String speaker_bio;
    private byte[] speaker_photo;

    @ManyToMany(mappedBy = "speakers")
    @JsonIgnore
    List<Session> sessions;

    @ManyToMany(mappedBy = "speakers")
    @JsonIgnore
    List<Workshop> workshops;


}

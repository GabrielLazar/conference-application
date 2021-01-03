package com.gabriellazar.conferenceapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name = "workshops")
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workshop_id;
    private String workshop_name;
    private String description;
    private String requirements;
    private String room;
    private Integer capacity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "workshop_speakers",
            joinColumns = @JoinColumn(name = "workshop_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id")
        )
    List<Speaker> speakers;
}

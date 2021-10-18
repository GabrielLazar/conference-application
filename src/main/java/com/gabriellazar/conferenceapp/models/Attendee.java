package com.gabriellazar.conferenceapp.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;


@Entity(name = "attendees")
@Getter
@Setter
@NoArgsConstructor
public class Attendee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendeeId;
    private String firstName;
    private String lastName;
    private String password;
    private String title;
    private String company;
    @Column(unique = true)
    private String email;
    @Length(min = 10, max = 10)
    private String phoneNumber;

}

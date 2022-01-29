package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Attendee;
import com.gabriellazar.conferenceapp.services.AttendeeService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityService implements UserDetailsService {

    private AttendeeService attendeeService;

    public SecurityService(AttendeeService attendeeService) {
        this.attendeeService = attendeeService;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        Attendee attendee = attendeeService.getAttendeeByEmail(email);
        if (attendee == null) {
            throw new DataNotFoundException("Attendee not found with email :: " + email);
        }
        return new org.springframework.security.core.userdetails.User(attendee.getEmail(), attendee.getPassword(), getAuthority(attendee));
    }

    public List<GrantedAuthority> getAuthority(Attendee attendee) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(attendee.getRole()));
        return grantedAuthorities;
    }
}

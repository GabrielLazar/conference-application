package com.gabriellazar.conferenceapp.services.impl;

import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Session;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.models.Workshop;
import com.gabriellazar.conferenceapp.repositories.SessionRepository;
import com.gabriellazar.conferenceapp.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;

    private SessionService target;

    @BeforeEach
    public void setUp() {
        target = new SessionServiceImpl(sessionRepository);
    }

    @Test
    public void testGetSessionByIdHappyPath() {
        Session expected = new Session(1L,"Intro to Java","Learn java basics",120,Collections.EMPTY_LIST);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(expected));

        Session actual = target.getSessionById(1L);

        verify(sessionRepository, times(1)).findById(1L);
        assertEquals(expected.getSession_id(), actual.getSession_id());
        assertEquals(expected.getSession_name(), actual.getSession_name());
        assertEquals(expected.getSession_description(), actual.getSession_description());
        assertEquals(expected.getSession_length(), actual.getSession_length());
    }

    @Test
    public void testGetSessionByIdThrowException() {
        assertThatThrownBy(() -> target.getSessionById(0L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("Session not found with this id :: 0");
    }

}
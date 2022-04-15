package com.gabriellazar.conferenceapp.services.impl;


import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.repositories.SpeakerRepository;
import com.gabriellazar.conferenceapp.services.SpeakerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class SpeakerServiceImplTest {

    @Mock
    private SpeakerRepository speakerRepository;

    SpeakerService target;

    @BeforeEach
    public void setUp() {
        target = new SpeakerServiceImpl(speakerRepository);
    }

    @Test
    public void testGetSpeakerByIdThrowException() {
        assertThatThrownBy(() -> target.getSpeakerById(0L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("Speaker not found with this id :: 0");
    }
}
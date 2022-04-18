package com.gabriellazar.conferenceapp.services.impl;


import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.repositories.SpeakerRepository;
import com.gabriellazar.conferenceapp.services.SpeakerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    public void testGetSpeakerById() {
        Speaker expected = new Speaker(1L, "first_name", "last_name", "dev", "company", "bio", new byte[1], null, null);
        when(speakerRepository.findById(1L)).thenReturn(Optional.of(expected));
        Speaker actual = target.getSpeakerById(1L);

        verify(speakerRepository, times(1)).findById(1L);
        assertEquals(expected.getSpeaker_id(), actual.getSpeaker_id());
        assertEquals(expected.getFirst_name(), actual.getFirst_name());
        assertEquals(expected.getLast_name(), actual.getLast_name());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getCompany(), actual.getCompany());
        assertEquals(expected.getSpeaker_bio(), actual.getSpeaker_bio());
    }

    @Test
    public void testGetSpeakerByIdThrowException() {
        assertThatThrownBy(() -> target.getSpeakerById(0L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("Speaker not found with this id :: 0");
    }
}
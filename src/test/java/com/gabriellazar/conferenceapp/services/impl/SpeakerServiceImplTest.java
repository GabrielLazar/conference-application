package com.gabriellazar.conferenceapp.services.impl;


import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.models.Workshop;
import com.gabriellazar.conferenceapp.repositories.SpeakerRepository;
import com.gabriellazar.conferenceapp.services.SpeakerService;
import com.gabriellazar.conferenceapp.utils.UnitTestAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    @Test
    public void testCreateSpeaker() {
        Speaker expected = new Speaker(1L, "first_name", "last_name", "dev", "company", "bio", new byte[1], null, null);

        when(speakerRepository.saveAndFlush(expected)).thenReturn(expected);
        when(speakerRepository.findById(1L)).thenReturn(Optional.of(expected));

        Speaker actual = target.createSpeaker(expected);

        verify(speakerRepository, times(1)).saveAndFlush(any());
        verify(speakerRepository, times(1)).findById(anyLong());

        assertEquals(expected.getSpeaker_id(), actual.getSpeaker_id());
        assertEquals(expected.getFirst_name(), actual.getFirst_name());
        assertEquals(expected.getLast_name(), actual.getLast_name());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getCompany(), actual.getCompany());
        assertEquals(expected.getSpeaker_bio(), actual.getSpeaker_bio());

        assertNotNull(UnitTestAppender.findLoggingEvent("Successfully saved speaker"));
    }

    @Test
    public void testDeleteSpeakerById() {
        doNothing().when(speakerRepository).deleteById(1L);
        target.deleteSpeakerById(1L);
        verify(speakerRepository, times(1)).deleteById(anyLong());
    }

}
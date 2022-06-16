package com.gabriellazar.conferenceapp.services.impl;


import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.repositories.SpeakerRepository;
import com.gabriellazar.conferenceapp.services.SpeakerService;
import com.gabriellazar.conferenceapp.utils.UnitTestAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void testGetAllSpeakersWithNoFilters() {
        Speaker expected1 = new Speaker(1L, "James", "Bond", "dev", "Secret", "bio", new byte[1], null, null);
        Speaker expected2 = new Speaker(2L, "John", "Doe", "qa", "abcCompany", "bio", new byte[1], null, null);

        when(speakerRepository.findAll()).thenReturn(Arrays.asList(expected1, expected2));

        List<Speaker> actualList = target.getAllSpeakers(Optional.empty(), Optional.empty());

        assertEquals(2, actualList.size());
        verify(speakerRepository, times(1)).findAll();
        Comparator<Speaker> speakerComparator = Comparator.comparing(Speaker::getSpeaker_id).thenComparing(Speaker::getFirst_name)
                .thenComparing(Speaker::getLast_name).thenComparing(Speaker::getTitle).thenComparing(Speaker::getCompany)
                .thenComparing(Speaker::getSpeaker_bio);

        assertThat(actualList).usingElementComparator(speakerComparator)
                .usingElementComparatorIgnoringFields("speaker_photo","sessions","workshops").contains(expected1);

        assertThat(actualList).usingElementComparator(speakerComparator)
                .usingElementComparatorIgnoringFields("speaker_photo","sessions","workshops").contains(expected2);
    }

    @Test
    public void testGetAllSpeakersWithJobTitle() {
        Speaker expected1 = new Speaker(1L, "James", "Bond", "dev", "Secret", "bio", new byte[1], null, null);
        Speaker expected2 = new Speaker(2L, "John", "Doe", "qa", "abcCompany", "bio", new byte[1], null, null);

        when(speakerRepository.findAll()).thenReturn(Arrays.asList(expected1, expected2));

        List<Speaker> actualList = target.getAllSpeakers(Optional.empty(),Optional.of("dev"));

        assertEquals(1, actualList.size());
        verify(speakerRepository, times(1)).findAll();
        Comparator<Speaker> speakerComparator = Comparator.comparing(Speaker::getSpeaker_id).thenComparing(Speaker::getFirst_name)
                .thenComparing(Speaker::getLast_name).thenComparing(Speaker::getTitle).thenComparing(Speaker::getCompany)
                .thenComparing(Speaker::getSpeaker_bio);

        assertThat(actualList).usingElementComparator(speakerComparator)
                .usingElementComparatorIgnoringFields("speaker_photo","sessions","workshops").contains(expected1);

    }

    @Test
    public void testGetAllSpeakersWithCompanyName() {
        Speaker expected1 = new Speaker(1L, "James", "Bond", "dev", "Secret", "bio", new byte[1], null, null);
        Speaker expected2 = new Speaker(2L, "John", "Doe", "qa", "abcCompany", "bio", new byte[1], null, null);

        when(speakerRepository.findAll()).thenReturn(Arrays.asList(expected1, expected2));

        List<Speaker> actualList = target.getAllSpeakers(Optional.of("Secret"), Optional.empty());

        assertEquals(1, actualList.size());
        verify(speakerRepository, times(1)).findAll();
        Comparator<Speaker> speakerComparator = Comparator.comparing(Speaker::getSpeaker_id).thenComparing(Speaker::getFirst_name)
                .thenComparing(Speaker::getLast_name).thenComparing(Speaker::getTitle).thenComparing(Speaker::getCompany)
                .thenComparing(Speaker::getSpeaker_bio);

        assertThat(actualList).usingElementComparator(speakerComparator)
                .usingElementComparatorIgnoringFields("speaker_photo","sessions","workshops").contains(expected1);

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
    public void testEditSpeakerById(){

        Speaker expected = new Speaker(1L, "first_name", "last_name", "dev",
                "company", "bio", new byte[1], null, null);

        when(speakerRepository.saveAndFlush(expected)).thenReturn(expected);
        when(speakerRepository.findById(1L)).thenReturn(Optional.of(expected));

        Speaker actual = target.editSpeakerById(1L, expected);

        verify(speakerRepository, times(1)).saveAndFlush(any());
        verify(speakerRepository, times(2)).findById(anyLong());

        assertEquals(expected.getFirst_name(),actual.getFirst_name());
        assertEquals(expected.getLast_name(),actual.getLast_name());
        assertEquals(expected.getTitle(),actual.getTitle());
        assertEquals(expected.getCompany(),actual.getCompany());
        assertEquals(expected.getSpeaker_bio(),actual.getSpeaker_bio());

    }

    @Test
    public void testDeleteSpeakerById() {
        doNothing().when(speakerRepository).deleteById(1L);
        target.deleteSpeakerById(1L);
        verify(speakerRepository, times(1)).deleteById(anyLong());
    }

}
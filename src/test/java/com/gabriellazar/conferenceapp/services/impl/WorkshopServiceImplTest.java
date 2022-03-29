package com.gabriellazar.conferenceapp.services.impl;


import com.gabriellazar.conferenceapp.exceptions.DataNotFoundException;
import com.gabriellazar.conferenceapp.models.Speaker;
import com.gabriellazar.conferenceapp.models.Workshop;
import com.gabriellazar.conferenceapp.repositories.WorkshopRepository;
import com.gabriellazar.conferenceapp.services.WorkshopService;
import com.gabriellazar.conferenceapp.utils.UnitTestAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkshopServiceImplTest {

    @Mock
    private WorkshopRepository workshopRepository;

    WorkshopService target;

    @BeforeEach
    public void setUp() {
        target = new WorkshopServiceImpl(workshopRepository);
    }

    @Test
    public void testGetWorkshopByIdHappyPath() {
        Workshop actual = new Workshop(1L, "Java Fundamentals", "description",
                "requirements", "room", 100, Collections.singletonList(new Speaker()));
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(actual));

        Workshop expected = target.getWorkshopById(1L);

        verify(workshopRepository, times(1)).findById(1L);
        assertEquals(expected.getWorkshop_id(), actual.getWorkshop_id());
        assertEquals(expected.getWorkshop_name(), actual.getWorkshop_name());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getRequirements(), actual.getRequirements());
        assertEquals(expected.getRoom(), actual.getRoom());
        assertEquals(expected.getCapacity(), actual.getCapacity());

    }

    @Test
    public void testGetWorkshopByIdThrowException() {
        assertThatThrownBy(() -> target.getWorkshopById(0L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessage("Workshop not found with this id :: 0");
    }

    @Test
    public void testCreateWorkshop() {
        Workshop actual = new Workshop(1L, "Java Fundamentals", "description",
                "requirements", "room", 100, Collections.singletonList(new Speaker()));

        when(workshopRepository.saveAndFlush(actual)).thenReturn(actual);
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(actual));

        Workshop expected = target.createWorkshop(actual);

        verify(workshopRepository, times(1)).saveAndFlush(any());

        assertEquals(expected.getWorkshop_id(), actual.getWorkshop_id());
        assertEquals(expected.getWorkshop_name(), actual.getWorkshop_name());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getRequirements(), actual.getRequirements());
        assertEquals(expected.getRoom(), actual.getRoom());
        assertEquals(expected.getCapacity(), actual.getCapacity());

        assertNotNull(UnitTestAppender.findLoggingEvent("Successfully saving workshop"));
    }

    @Test
    public void testDeleteWorkshopById() {
        doNothing().when(workshopRepository).deleteById(1L);
        target.deleteWorkshopById(1L);
        verify(workshopRepository, times(1)).deleteById(anyLong());
    }

}
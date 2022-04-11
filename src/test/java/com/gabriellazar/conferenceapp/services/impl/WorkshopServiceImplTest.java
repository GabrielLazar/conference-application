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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


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

    @Test
    public void testEditWorkshopById() {
        Workshop actual = new Workshop(1L, "Java Fundamentals", "description",
                "requirements", "room", 100, Collections.singletonList(new Speaker()));

        when(workshopRepository.saveAndFlush(actual)).thenReturn(actual);
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(actual));

        Workshop expected = target.editWorkshopById(1L, actual);

        verify(workshopRepository, times(1)).saveAndFlush(any());
        verify(workshopRepository, times(2)).findById(anyLong());

        assertEquals(expected.getWorkshop_id(), actual.getWorkshop_id());
        assertEquals(expected.getWorkshop_name(), actual.getWorkshop_name());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getRequirements(), actual.getRequirements());
        assertEquals(expected.getRoom(), actual.getRoom());
        assertEquals(expected.getCapacity(), actual.getCapacity());
    }

    @Test
    public void testGetAllWorkshopWithoutRoom(){
        Workshop workshop1 = new Workshop(1L, "Java Fundamentals", "Java Class",
                "No requirements", "Room A", 100, Collections.singletonList(new Speaker()));

        Workshop workshop2 = new Workshop(2L, "C#Fundamentals", "C# class",
                "No requirements", "Room B", 100, Collections.singletonList(new Speaker()));

        when(workshopRepository.findAll()).thenReturn((Arrays.asList(workshop1,workshop2)));

        List<Workshop> workshopList = target.getAllWorkshop(Optional.empty());
        Comparator<Workshop> workshopComparator = Comparator.comparing(Workshop::getWorkshop_id).thenComparing(Workshop::getWorkshop_name)
                .thenComparing(Workshop::getDescription).thenComparing(Workshop::getRequirements).thenComparing(Workshop::getRoom)
                .thenComparing(Workshop::getCapacity);

        assertEquals(2,workshopList.size());
        verify(workshopRepository, times(1)).findAll();

        assertThat(workshopList).usingElementComparator(workshopComparator)
                .usingElementComparatorIgnoringFields("speakers").contains(workshop1);

        assertThat(workshopList).usingElementComparator(workshopComparator)
                .usingElementComparatorIgnoringFields("speakers").contains(workshop2);
    }

    @Test
    public void testGetAllWorkshopWithRoom(){
        Workshop workshop1 = new Workshop(1L, "Java Fundamentals", "Java Class",
                "No requirements", "Room A", 100, Collections.singletonList(new Speaker()));

        Workshop workshop2 = new Workshop(2L, "C#Fundamentals", "C# class",
                "No requirements", "Room B", 100, Collections.singletonList(new Speaker()));

        when(workshopRepository.findAll()).thenReturn((Arrays.asList(workshop1,workshop2)));

        List<Workshop> workshopList = target.getAllWorkshop(Optional.of("Room A"));

        assertEquals(1,workshopList.size());
        verify(workshopRepository, times(1)).findAll();

        Workshop actual = workshopList.get(0);

        assertEquals(workshop1.getWorkshop_id(), actual.getWorkshop_id());
        assertEquals(workshop1.getWorkshop_name(), actual.getWorkshop_name());
        assertEquals(workshop1.getDescription(), actual.getDescription());
        assertEquals(workshop1.getRequirements(), actual.getRequirements());
        assertEquals(workshop1.getRoom(), actual.getRoom());
        assertEquals(workshop1.getCapacity(), actual.getCapacity());

    }
}
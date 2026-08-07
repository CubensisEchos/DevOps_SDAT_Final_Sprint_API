package com.keyin.aircraft;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceTest
{
    @Mock
    private AircraftRepository aircraftRepository;

    @InjectMocks
    private AircraftService aircraftService;

    Aircraft aircraft;
    Aircraft aircraft2;
    Aircraft aircraft3;

    List<Aircraft> aircraftList;

    @BeforeEach
    void setup()
    {
        aircraft = new Aircraft("Test name 1", 40);
        aircraft2 = new Aircraft("Test name 2", 62);
        aircraft3 = new Aircraft("Test name 3", 57);

        aircraftList = List.of(aircraft, aircraft2, aircraft3);
    }

    @AfterEach
    void tearDown()
    {
        aircraft = null;
        aircraft2 = null;
        aircraft3 = null;

        aircraftList = null;
    }

    @Test
    public void addNewAircraft_ReturnsNewAircraft()
    {
        Mockito.when(aircraftRepository.save(aircraft)).thenReturn(aircraft);
        Aircraft savedAircraft = aircraftService.addNewAircraft(aircraft);

        Assertions.assertEquals(aircraft, savedAircraft);
        verify(aircraftRepository).save(aircraft);
    }

    @Test
    public void returnFullAircraftList()
    {
        Mockito.when(aircraftRepository.findAll()).thenReturn(aircraftList);
        Iterable<Aircraft> expected = aircraftService.getAllAircraft();

        Assertions.assertEquals(aircraftList, expected);
    }

    @Test
    public void findAircraftById_ReturnsAircraftWhenFound()
    {
        Mockito.when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        Optional<Aircraft> expected = aircraftService.getAircraftById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(aircraft, expected.get());
    }

    @Test
    public void findAircraftById_ReturnsEmptyWhenMissing()
    {
        Mockito.when(aircraftRepository.findById(66L)).thenReturn(Optional.empty());
        Optional<Aircraft> expected = aircraftService.getAircraftById(66L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void updateAircraft_ReturnsUpdatedAircraft()
    {
        Aircraft updatedAircraft = new Aircraft("Test name", 66);

        Mockito.when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        Mockito.when(aircraftRepository.save(Mockito.any(Aircraft.class))).thenReturn(updatedAircraft);
        Optional<Aircraft> expected = aircraftService.updateAircraft(1L, updatedAircraft);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Test name", expected.get().getAircraftName());
        Assertions.assertEquals(66, expected.get().getNumberOfPassengers());
    }

    @Test
    public void updateAircraft_ReturnsAircraftNotFound()
    {
        Aircraft updatedAircraft = new Aircraft("Test name", 66);

        Mockito.when(aircraftRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Aircraft> expected = aircraftService.updateAircraft(1L, updatedAircraft);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deleteAircraftById_ReturnsTrueWhenDeleted()
    {
        Mockito.when(aircraftRepository.existsById(1L)).thenReturn(true);
        boolean deleted = aircraftService.deleteAircraftById(1L);

        Assertions.assertTrue(deleted);
        verify(aircraftRepository).deleteById(1L);
    }

    @Test
    public void deleteAircraftById_ReturnsFalseWhenMissing()
    {
        Mockito.when(aircraftRepository.existsById(66L)).thenReturn(false);
        boolean deleted = aircraftService.deleteAircraftById(66L);

        Assertions.assertFalse(deleted);
        verify(aircraftRepository, never()).deleteById(anyLong());
    }
}

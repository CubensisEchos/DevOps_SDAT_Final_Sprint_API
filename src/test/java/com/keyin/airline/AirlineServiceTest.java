package com.keyin.airline;

import com.keyin.aircraft.Aircraft;
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
public class AirlineServiceTest
{
    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private AirlineService airlineService;

    Airline airline;
    Airline airline2;
    Airline airline3;

    List<Airline> airlineList;

    @BeforeEach
    void setup()
    {
        airline = new Airline("Test name", "Test code", "Test country");
        airline2 = new Airline("Test name 2", "Test code 2", "Test country 2");
        airline3 = new Airline("Test name 3", "Test code 3", "Test country 3");

        airlineList = List.of(airline, airline2, airline3);
    }

    @AfterEach
    void tearDown()
    {
        airline = null;
        airline2 = null;
        airline3 = null;

        airlineList = null;
    }

    @Test
    public void addNewAirline_ReturnsNewAirline()
    {
        Mockito.when(airlineRepository.save(airline)).thenReturn(airline);
        Airline savedAirline = airlineService.addNewAirline(airline);

        Assertions.assertEquals(airline, savedAirline);
        verify(airlineRepository).save(airline);
    }

    @Test
    public void returnsFullAirlineList()
    {
        Mockito.when(airlineRepository.findAll()).thenReturn(airlineList);
        Iterable<Airline> expected = airlineService.getAllAirlines();

        Assertions.assertEquals(airlineList, expected);
    }

    @Test
    public void findAirlineById_ReturnsAirlineWhenFound()
    {
        Mockito.when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        Optional<Airline> expected = airlineService.getAirlineById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(airline, expected.get());
    }

    @Test
    public void findAirlineByID_ReturnsEmptyWhenMissing()
    {
        Mockito.when(airlineRepository.findById(66L)).thenReturn(Optional.empty());
        Optional<Airline> expected = airlineService.getAirlineById(66L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void updateAirline_ReturnsUpdatedLine()
    {
        Airline updatedAirline = new Airline("Test name", "Test code", "Test country");

        Mockito.when(airlineRepository.findById(1L)).thenReturn(Optional.of(airline));
        Mockito.when(airlineRepository.save(Mockito.any(Airline.class))).thenReturn(updatedAirline);
        Optional<Airline> expected = airlineService.updateAirline(1L, updatedAirline);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Test name", expected.get().getAirlineName());
        Assertions.assertEquals("Test code", expected.get().getAirlineCode());
        Assertions.assertEquals("Test country", expected.get().getAirlineCountry());
    }

    @Test
    public void updateAirline_ReturnsAirlineNotFound()
    {
        Airline updatedAirline = new Airline("Test name", "Test code", "Test country");

        Mockito.when(airlineRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Airline> expected = airlineService.updateAirline(1L, updatedAirline);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deleteAirlineById_ReturnsTrueWhenDeleted()
    {
        Mockito.when(airlineRepository.existsById(1L)).thenReturn(true);
        boolean deleted = airlineService.deleteAirlineById(1L);

        Assertions.assertTrue(deleted);
        verify(airlineRepository).deleteById(1L);
    }

    @Test
    public void deleteAircraftById_ReturnsFalseWhenMissing()
    {
        Mockito.when(airlineRepository.existsById(66L)).thenReturn(false);
        boolean deleted = airlineService.deleteAirlineById(66L);

        Assertions.assertFalse(deleted);
        verify(airlineRepository, never()).deleteById(anyLong());
    }
}

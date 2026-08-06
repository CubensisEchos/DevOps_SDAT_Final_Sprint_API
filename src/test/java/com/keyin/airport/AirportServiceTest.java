package com.keyin.airport;

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
public class AirportServiceTest
{
    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    Airport airport;
    Airport airport2;
    Airport airport3;

    List<Airport> airportList;

    @BeforeEach
    void setup()
    {
        airport = new Airport("Test airport 1", "Test code 1", "Test city", "Test province", "Test country");
        airport2 = new Airport("Test airport 2", "Test code 2","Test city 2", "Test province 2", "Test country 2");
        airport3 = new Airport("Test airport 3", "Test code 3","Test city 3", "Test province 3", "Test country 3");

        airportList = List.of(airport, airport2, airport3);
    }

    @AfterEach
    void tearDown()
    {
        airport = null;
        airport2 = null;
        airport3 = null;
    }

    @Test
    public void addNewAirport_ReturnsNewAirport()
    {
        Mockito.when(airportRepository.save(airport)).thenReturn(airport);
        Airport newAirport = airportService.createAirport(airport);

        Assertions.assertEquals(airport, newAirport);
        verify(airportRepository).save(airport);
    }

    @Test
    public void returnAllAirports()
    {
        Mockito.when(airportRepository.findAll()).thenReturn(airportList);
        Iterable<Airport> expected = airportService.getAllAirports();

        Assertions.assertEquals(airportList, expected);
    }

    @Test
    public void findAirportById_ReturnsAirportWhenFound()
    {
        Mockito.when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        Optional<Airport> expected = airportService.getAirportById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(airport, expected.get());
    }

    @Test
    public void findAirportById_ReturnsEmptyWhenMissing()
    {
        Mockito.when(airportRepository.findById(11L)).thenReturn(Optional.empty());
        Optional<Airport> expected = airportService.getAirportById(11L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void updateAirport_ReturnsUpdatedAirport()
    {
        Airport updatedAirport = new Airport("Updated airport 1", "Updated code 1", "Updated city", "Updated province", "Updated country");

        Mockito.when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));
        Mockito.when(airportRepository.save(Mockito.any(Airport.class))).thenReturn(updatedAirport);
        Optional<Airport> expected = airportService.updateAirport(1L, updatedAirport);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Updated airport 1", expected.get().getAirportName());
        Assertions.assertEquals("Updated code 1", expected.get().getAirportCode());
        Assertions.assertEquals("Updated city", expected.get().getCity());
    }

    @Test
    public void updateAirport_AirportNotFound()
    {
        Airport updatedAirport = new Airport("Updated airport 1", "Updated code 1", "Updated city", "Updated province", "Updated country");

        Mockito.when(airportRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Airport> expected = airportService.updateAirport(1L, updatedAirport);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deleteAirport_ReturnsTrueWhenDeleted()
    {
        Mockito.when(airportRepository.existsById(1L)).thenReturn(true);
        boolean deleted = airportService.deleteAirport(1L);

        Assertions.assertTrue(deleted);
        verify(airportRepository).deleteById(1L);
    }

    @Test
    public void deleteAirport_ReturnsFalseWhenMissing()
    {
        Mockito.when(airportRepository.existsById(66L)) .thenReturn(false);
        boolean deleted = airportService.deleteAirport(66L);

        Assertions.assertFalse(deleted);
        verify(airportRepository, never()).deleteById(anyLong());
    }
}

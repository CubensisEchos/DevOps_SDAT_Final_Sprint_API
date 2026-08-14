package com.keyin.flight;

import com.keyin.aircraft.Aircraft;
import com.keyin.enums.FlightStatus;
import com.keyin.gate.Gate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest
{
    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    Flight flight;
    Flight flight2;
    Flight flight3;

    List<Flight> flightList;

    @BeforeEach
    void setup()
    {
        flight = new Flight(
                "Test",
                LocalDateTime.of(2026, 8, 10, 9, 30),
                LocalDateTime.of(2026, 8, 10, 11, 30),
                FlightStatus.SCHEDULED
        );

        flight2 = new Flight(
                "Test",
                LocalDateTime.of(2026, 7, 10, 9, 30),
                LocalDateTime.of(2026, 7, 10, 11, 45),
                FlightStatus.DELAYED
        );

        flight3 = new Flight(
                "Test",
                LocalDateTime.of(2026, 8, 13, 8, 30),
                LocalDateTime.of(2026, 8, 13, 11, 45),
                FlightStatus.SCHEDULED
        );

        flightList = List.of(flight, flight2, flight3);
    }

    @AfterEach
    void tearDown()
    {
        flight = null;
        flight2 = null;
        flight3 = null;

        flightList = null;
    }

    @Test
    public void addNewFlight_ReturnsNewFlight()
    {
        Mockito.when(flightRepository.save(flight)).thenReturn(flight);
        Flight savedFlight = flightService.addNewFlight(flight);

        Assertions.assertEquals(flight, savedFlight);
        verify(flightRepository).save(flight);
    }

    @Test
    public void returnFullFlightList()
    {
        Mockito.when(flightRepository.findAll()).thenReturn(flightList);
        Iterable<Flight> expected = flightService.getAllFlights();

        Assertions.assertEquals(flightList, expected);
    }

    @Test
    public void findFlightById_ReturnsFlightWhenFound()
    {
        Mockito.when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        Optional<Flight> expected = flightService.getFlightById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(flight, expected.get());
    }

    @Test
    public void getFlightById_ReturnsEmptyWhenMissing()
    {
        Mockito.when(flightRepository.findById(88L)).thenReturn(Optional.empty());
        Optional<Flight> expected = flightService.getFlightById(88L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public  void updateFlight_ReturnsUpdatedFlight()
    {
        Flight updatedFlight = new Flight(
                "Test num",
                LocalDateTime.of(2026, 3, 11, 9, 30),
                LocalDateTime.of(2026, 3, 13, 11, 30),
                FlightStatus.SCHEDULED
        );

        Mockito.when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        Mockito.when(flightRepository.save(Mockito.any(Flight.class))).thenReturn(updatedFlight);
        Optional<Flight> expected = flightService.updateFlight(1L, updatedFlight);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Test num", expected.get().getFlightNumber());
        Assertions.assertEquals(LocalDateTime.of(2026, 3, 11, 9, 30), expected.get().getScheduledDepartureTime());
        Assertions.assertEquals(LocalDateTime.of(2026, 3, 13, 11, 30), expected.get().getScheduledArrival());
        Assertions.assertEquals(FlightStatus.SCHEDULED, expected.get().getStatus());
    }

    @Test
    public void updateFlight_ReturnsFlightNotFound()
    {
        Flight updatedFlight = new Flight(
                "Test num",
                LocalDateTime.of(2026, 3, 11, 9, 30),
                LocalDateTime.of(2026, 3, 13, 11, 30),
                FlightStatus.SCHEDULED
        );

        Mockito.when(flightRepository.findById(88L)).thenReturn(Optional.empty());
        Optional<Flight> expected = flightService.updateFlight(88L, updatedFlight);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deleteFlight_ReturnsTrueWhenDeleted()
    {
        Mockito.when(flightRepository.existsById(1L)).thenReturn(true);
        boolean deleted = flightService.deleteFlightById(1L);

        Assertions.assertTrue(deleted);
        verify(flightRepository).deleteById(1L);
    }

    @Test
    public void deleteFlight_ReturnsFalseWhenMissing()
    {
        Mockito.when(flightRepository.existsById(88L)).thenReturn(false);
        boolean deleted = flightService.deleteFlightById(88L);

        Assertions.assertFalse(deleted);
        verify(flightRepository, never()).deleteById(anyLong());
    }

    @Test
    public void getDepartures_ReturnsFlightsWhenFound()
    {
        Mockito.when(flightRepository.findByDepartureAirportId(1L)).thenReturn(flightList);
        List<Flight> expected = flightService.getDepartures(1L);

        Assertions.assertEquals(flightList, expected);
        verify(flightRepository).findByDepartureAirportId(1L);
    }

    @Test
    public void getDepartures_ReturnsEmptyWhenMissing()
    {
        Mockito.when(flightRepository.findByDepartureAirportId(88L)).thenReturn(List.of());
        List<Flight> expected = flightService.getDepartures(88L);

        Assertions.assertTrue(expected.isEmpty());
        verify(flightRepository).findByDepartureAirportId(88L);
    }

    @Test
    public void getArrivals_ReturnsFlightsWhenFound()
    {
        Mockito.when(flightRepository.findByArrivalAirportId(1L)).thenReturn(flightList);
        List<Flight> expected = flightService.getArrivals(1L);

        Assertions.assertEquals(flightList, expected);
        verify(flightRepository).findByArrivalAirportId(1L);
    }

    @Test
    public void getArrivals_ReturnsEmptyWhenMissing()
    {
        Mockito.when(flightRepository.findByArrivalAirportId(88L)).thenReturn(List.of());
        List<Flight> expected = flightService.getArrivals(88L);

        Assertions.assertTrue(expected.isEmpty());
        verify(flightRepository).findByArrivalAirportId(88L);
    }

    @Test
    public void getFlightsByAirline_ReturnsFlightsWhenFound()
    {
        Mockito.when(flightRepository.findByAirlineId(1L)).thenReturn(flightList);
        List<Flight> expected = flightService.getFlightsByAirline(1L);

        Assertions.assertEquals(flightList, expected);
        verify(flightRepository).findByAirlineId(1L);
    }

    @Test
    public void getFlightsByAirline_ReturnsEmptyWhenMissing()
    {
        Mockito.when(flightRepository.findByAirlineId(88L)).thenReturn(List.of());
        List<Flight> expected = flightService.getFlightsByAirline(88L);

        Assertions.assertTrue(expected.isEmpty());
        verify(flightRepository).findByAirlineId(88L);
    }

    @Test
    public void getDepartures_ReturnsFlightWithGateAircraftAndDepartureTime()
    {
        Gate gate = new Gate();
        gate.setGateNumber("A12");

        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftName("Test name");

        flight.setGate(gate); flight.setAircraft(aircraft);
        flight.setScheduledDepartureTime(LocalDateTime.of(2026, 8, 13, 22, 30));

        Mockito.when(flightRepository.findByDepartureAirportId(1L)).thenReturn(List.of(flight));
        List<Flight> expected = flightService.getDepartures(1L);

        Assertions.assertEquals(1, expected.size());
        Assertions.assertEquals("A12", expected.get(0).getGate().getGateNumber());
        Assertions.assertEquals("Test name", expected.get(0).getAircraft().getAircraftName());
        Assertions.assertEquals(LocalDateTime.of(2026, 8, 13, 22, 30), expected.get(0).getScheduledDepartureTime());
        verify(flightRepository).findByDepartureAirportId(1L);
    }

    @Test
    public void getArrivals_ReturnsFlightWithGateAircraftAndArrivalTime()
    {
        Gate gate = new Gate();
        gate.setGateNumber("B7");

        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftName("Test name");

        flight.setGate(gate); flight.setAircraft(aircraft);
        flight.setScheduledArrival(LocalDateTime.of(2026, 8, 13, 18, 45));

        Mockito.when(flightRepository.findByArrivalAirportId(1L)).thenReturn(List.of(flight));
        List<Flight> expected = flightService.getArrivals(1L);

        Assertions.assertEquals(1, expected.size());
        Assertions.assertEquals("B7", expected.get(0).getGate().getGateNumber());
        Assertions.assertEquals("Test name", expected.get(0).getAircraft().getAircraftName());
        Assertions.assertEquals(LocalDateTime.of(2026, 8, 13, 18, 45), expected.get(0).getScheduledArrival());
        verify(flightRepository).findByArrivalAirportId(1L);
    }
}

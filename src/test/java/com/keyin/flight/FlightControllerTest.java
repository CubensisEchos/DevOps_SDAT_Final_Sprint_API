package com.keyin.flight;

import com.keyin.enums.FlightStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FlightControllerTest
{
    @Mock
    private FlightService flightService;

    @InjectMocks
    private  FlightController flightController;

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
    public void createFlightDelegatesAndReturns()
    {
        Flight createdFlight = new Flight();
        createdFlight.setId(23L);
        createdFlight.setFlightNumber("1A");
        createdFlight.setScheduledArrival(LocalDateTime.of(2026, 9, 13, 8, 30));
        createdFlight.setScheduledDepartureTime(LocalDateTime.of(2026, 8, 13, 22, 30));
        createdFlight.setStatus(FlightStatus.ARRIVED);

        Mockito.when(flightService.addNewFlight(any(Flight.class))).thenReturn(createdFlight);
        Flight response = flightController.addNewFlight(new Flight());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(23L, response.getId());
        Assertions.assertEquals("1A", response.getFlightNumber());
        Assertions.assertEquals(LocalDateTime.of(2026, 9, 13, 8, 30), response.getScheduledArrival());
        Assertions.assertEquals(LocalDateTime.of(2026, 8, 13, 22, 30), response.getScheduledDepartureTime());
        Assertions.assertEquals(FlightStatus.ARRIVED, response.getStatus());
        verify(flightService).addNewFlight(any(Flight.class));
    }

    @Test
    public void getAllFlights_ReturnsOkWhenFound()
    {
        Mockito.when(flightService.getAllFlights()).thenReturn(flightList);
        ResponseEntity<List<Flight>> response = flightController.getAllFlights();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(flightList, response.getBody());
    }

    @Test
    public void getFlightById_ReturnsOkWhenFound()
    {
        flight.setId(10L);

        Mockito.when(flightService.getFlightById(10L)).thenReturn(Optional.of(flight));
        ResponseEntity<Flight> response = flightController.getFlightById(10L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(10L, response.getBody().getId());
    }

    @Test
    public void getFlightById_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(flightService.getFlightById(88L)).thenReturn(Optional.empty());
        ResponseEntity<Flight> response = flightController.getFlightById(88L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateFlight_ReturnsUpdatedFlight()
    {
        Flight updatedFlight = new Flight();
        updatedFlight.setId(23L);
        updatedFlight.setFlightNumber("1A");
        updatedFlight.setScheduledDepartureTime(LocalDateTime.of(2026, 3, 11, 9, 30));
        updatedFlight.setScheduledArrival(LocalDateTime.of(2026, 3, 13, 11, 30));
        updatedFlight.setStatus(FlightStatus.BOARDING);

        Mockito.when(flightService.updateFlight(eq(23L), any(Flight.class))).thenReturn(Optional.of(updatedFlight));
        ResponseEntity<Flight> response = flightController.updateFlight(23L, new Flight());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(23L, response.getBody().getId());
        Assertions.assertEquals("1A", response.getBody().getFlightNumber());
        Assertions.assertEquals(LocalDateTime.of(2026, 3, 11, 9, 30), response.getBody().getScheduledDepartureTime());
        Assertions.assertEquals(LocalDateTime.of(2026, 3, 13, 11, 30), response.getBody().getScheduledArrival());
        Assertions.assertEquals(FlightStatus.BOARDING, response.getBody().getStatus());
    }

    @Test
    public void updateFlight_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(flightService.updateFlight(eq(88L), any(Flight.class))).thenReturn(Optional.empty());
        ResponseEntity<Flight> response = flightController.updateFlight(88L, new Flight());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void delete_ReturnsNothingWhenDeleted()
    {
        Mockito.when(flightService.deleteFlightById(1L)).thenReturn(true);
        ResponseEntity<Void> response = flightController.deleteFlight(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteFlight_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(flightService.deleteFlightById(88L)).thenReturn(false);
        ResponseEntity<Void> response = flightController.deleteFlight(88L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

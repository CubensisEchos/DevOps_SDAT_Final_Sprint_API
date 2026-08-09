package com.keyin.airline;

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

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AirlineControllerTest
{
    @Mock
    private AirlineService airlineService;

    @InjectMocks
    private AirlineController airlineController;

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
    public void createAirlineDelegatesAndReturns()
    {
        Airline createdAirline = new Airline();
        createdAirline.setId(88L);
        createdAirline.setAirlineName("Test name 1");
        createdAirline.setAirlineCode("Test code 1");
        createdAirline.setAirlineCountry("Test country 1");

        Mockito.when(airlineService.addNewAirline(any(Airline.class))).thenReturn(createdAirline);
        Airline response = airlineController.addNewAirline(new Airline());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(88L, response.getId());
        Assertions.assertEquals("Test name 1", response.getAirlineName());
        Assertions.assertEquals("Test code 1", response.getAirlineCode());
        Assertions.assertEquals("Test country 1", response.getAirlineCountry());
        verify(airlineService).addNewAirline(any(Airline.class));
    }

    @Test
    public void getAllAirline_ReturnsOkWhenFound()
    {
        Mockito.when(airlineService.getAllAirlines()).thenReturn(airlineList);
        ResponseEntity<List<Airline>> response = airlineController.getAllAirlines();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(airlineList, response.getBody());
    }

    @Test
    public void getAirLineById_ReturnsOkWhenFound()
    {
        airline.setId(10L);

        Mockito.when(airlineService.getAirlineById(10L)).thenReturn(Optional.of(airline));
        ResponseEntity<Airline> response = airlineController.getAirlineById(10L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(10L, response.getBody().getId());
    }

    @Test
    public void getAirlineById_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(airlineService.getAirlineById(66L)).thenReturn(Optional.empty());
        ResponseEntity<Airline> response = airlineController.getAirlineById(66L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateAirline_ReturnsUpdatedAirline()
    {
        Airline updatedAirline = new Airline();
        updatedAirline.setId(88L);
        updatedAirline.setAirlineName("Test name");
        updatedAirline.setAirlineCode("Test code");
        updatedAirline.setAirlineCountry("Test country");

        Mockito.when(airlineService.updateAirline(eq(88L), any(Airline.class))).thenReturn(Optional.of(updatedAirline));
        ResponseEntity<Airline> response = airlineController.updateAirline(88L, new Airline());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(88L, response.getBody().getId());
    }

    @Test
    public void updateAirline_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(airlineService.updateAirline(eq(66L), any(Airline.class))).thenReturn(Optional.empty());
        ResponseEntity<Airline> response = airlineController.updateAirline(66L, new Airline());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteAirline_ReturnsNothingWhenDeleted()
    {
        Mockito.when(airlineService.deleteAirlineById(1L)).thenReturn(true);
        ResponseEntity<Void> response = airlineController.deleteAirline(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteAirline_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(airlineService.deleteAirlineById(88L)).thenReturn(false);
        ResponseEntity<Void> response = airlineController.deleteAirline(88L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

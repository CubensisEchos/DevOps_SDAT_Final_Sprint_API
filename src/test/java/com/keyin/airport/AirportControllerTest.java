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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AirportControllerTest
{
    @Mock
    private AirportService airportService;

    @InjectMocks
    private  AirportController airportController;

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
    public void getAllAirports_ReturnsOkWhenFound()
    {
        Mockito.when(airportService.getAllAirports()).thenReturn(airportList);
        ResponseEntity<List<Airport>> response = airportController.getAllAirports();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(airportList, response.getBody());
    }

    @Test
    public void getAirportById_ReturnsOkWhenFound()
    {
        airport.setId(10L);

        Mockito.when(airportService.getAirportById(10L)).thenReturn(Optional.of(airport));
        ResponseEntity<Airport> response = airportController.getAirportById(10L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(10L, response.getBody().getId());
    }

    @Test
    public void getAirportById_ReturnsNotFoundWhenMissing()
     {
         Mockito.when(airportService.getAirportById(66L)).thenReturn(Optional.empty());
         ResponseEntity<Airport> response = airportController.getAirportById(66L);

         Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void updateAirport_ReturnsOkWhenUpdated()
     {
         Airport updatedAirport = new Airport();
         updatedAirport.setId(20L);
         updatedAirport.setAirportName("Test update name");
         updatedAirport.setAirportCode("Test update code");
         updatedAirport.setCity("Test update city");
         updatedAirport.setProvince("Test update province");
         updatedAirport.setCountry("Tests update country");

         Mockito.when(airportService.updateAirport(eq(20L), any(Airport.class))).thenReturn(Optional.of(updatedAirport));
         ResponseEntity<Airport> response = airportController.updateAirport(20L, new Airport());

         Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
         Assertions.assertNotNull(response.getBody());
         Assertions.assertEquals(20L, response.getBody().getId());
     }

     @Test
     public void updateAirport_ReturnsNotFoundWhenMissing()
     {
         Mockito.when(airportService.updateAirport(eq(40L), any(Airport.class))).thenReturn(Optional.empty());
         ResponseEntity<Airport> response = airportController.updateAirport(40L, new Airport());

         Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void deleteAirport_ReturnsNothingWhenDeleted()
     {
         Mockito.when(airportService.deleteAirport(1L)).thenReturn(true);
         ResponseEntity<Void> response = airportController.deleteAirport(1L);

         Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
     }

     @Test
     public void deleteAirport_ReturnsNotFoundWhenMissing()
     {
         Mockito.when(airportService.deleteAirport(66L)).thenReturn(false);
         ResponseEntity<Void> response = airportController.deleteAirport(66L);

         Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
     }

     @Test
     public void createsAirportDelegatesAndReturns()
     {
         Airport createdAirport = new Airport();
         createdAirport.setId(25L);
         createdAirport.setAirportName("Test Airport 25");

         Mockito.when(airportService.createAirport(any(Airport.class))).thenReturn(createdAirport);
         Airport response = airportController.createAirport(new Airport());

         Assertions.assertNotNull(response);
         Assertions.assertEquals(25L, response.getId());
         verify(airportService).createAirport(any(Airport.class));
     }
}

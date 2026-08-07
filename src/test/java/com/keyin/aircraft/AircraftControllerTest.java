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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AircraftControllerTest
{
    @Mock
    private AircraftService aircraftService;

    @InjectMocks
    private AircraftController aircraftController;

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
    public void createAircraftDelegatesAndReturns()
    {
        Aircraft createdAircraft = new Aircraft();
        createdAircraft.setId(88L);
        createdAircraft.setAircraftName("Test name");
        createdAircraft.setNumberOfPassengers(66);

        Mockito.when(aircraftService.addNewAircraft(any(Aircraft.class))).thenReturn(createdAircraft);
        Aircraft response = aircraftController.addNewAircraft(new Aircraft());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(88L, response.getId());
        Assertions.assertEquals("Test name", response.getAircraftName());
        Assertions.assertEquals(66, response.getNumberOfPassengers());
        verify(aircraftService).addNewAircraft(any(Aircraft.class));
    }

    @Test
    public void getAllAircraft_ReturnsOkWhenFound()
    {
        Mockito.when(aircraftService.getAllAircraft()).thenReturn(aircraftList);
        ResponseEntity<List<Aircraft>> response = aircraftController.getAllAircraft();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(aircraftList, response.getBody());
    }

    @Test
    public void getAircraftById_ReturnsOkWhenFound()
    {
        aircraft.setId(10L);

        Mockito.when(aircraftService.getAircraftById(10L)).thenReturn(Optional.of(aircraft));
        ResponseEntity<Aircraft> response = aircraftController.getAircraftById(10L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(10L, response.getBody().getId());
    }

    @Test
    public void getAircraftById_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(aircraftService.getAircraftById(66L)).thenReturn(Optional.empty());
        ResponseEntity<Aircraft> response = aircraftController.getAircraftById(66L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateAircraft_ReturnsOkWhenUpdated()
    {
        Aircraft updatedAircraft = new Aircraft();
        updatedAircraft.setId(44L);
        updatedAircraft.setAircraftName("Test name");
        updatedAircraft.setNumberOfPassengers(88);

        Mockito.when(aircraftService.updateAircraft(eq(44L), any(Aircraft.class))).thenReturn(Optional.of(updatedAircraft));
        ResponseEntity<Aircraft> response = aircraftController.updateAircraft(44L, new Aircraft());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(44L, response.getBody().getId());
    }

    @Test
    public void updateAircraft_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(aircraftService.updateAircraft(eq(66L), any(Aircraft.class))).thenReturn(Optional.empty());
        ResponseEntity<Aircraft> response = aircraftController.updateAircraft(66L, new Aircraft());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteAircraft_ReturnsNothingWhenDeleted()
    {
        Mockito.when(aircraftService.deleteAircraftById(1L)).thenReturn(true);
        ResponseEntity<Void> response = aircraftController.deleteAircraft(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteAircraft_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(aircraftService.deleteAircraftById(88L)).thenReturn(false);
        ResponseEntity<Void> response = aircraftController.deleteAircraft(88L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

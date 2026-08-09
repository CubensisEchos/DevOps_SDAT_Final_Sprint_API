package com.keyin.gate;

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
public class GateControllerTest
{
    @Mock
    private GateService gateService;

    @InjectMocks
    private GateController gateController;

    Gate gate;
    Gate gate2;
    Gate gate3;

    List<Gate> gateList;

    @BeforeEach
    void setup()
    {
        gate = new Gate("Test 1");
        gate2 = new Gate("Test 2");
        gate3 = new Gate("Test 3");

        gateList = List.of(gate, gate2, gate3);
    }

    @AfterEach
    void tearDown()
    {
        gate = null;
        gate2 = null;
        gate3 = null;

        gateList = null;
    }

    @Test
    public void createGateDelegatesAndReturns()
    {
        Gate createdGate = new Gate();
        createdGate.setId(23L);
        createdGate.setGateNumber("Test number 1");

        Mockito.when(gateService.addNewGate(any(Gate.class))).thenReturn(createdGate);
        Gate response = gateController.createNewGate(new Gate());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(23L, response.getId());
        Assertions.assertEquals("Test number 1", response.getGateNumber());
        verify(gateService).addNewGate(any(Gate.class));
    }

    @Test
    public void getAllGates_ReturnsOkWhenFound()
    {
        Mockito.when(gateService.getAllGates()).thenReturn(gateList);
        ResponseEntity<List<Gate>> response = gateController.getAllGates();

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(gateList, response.getBody());
    }

    @Test
    public void getGateById_ReturnsOkWhenFound()
    {
    gate.setId(10L);

    Mockito.when(gateService.getGateById(10L)).thenReturn(Optional.of(gate));
    ResponseEntity<Gate> response = gateController.getGateById(10L);

    Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    Assertions.assertNotNull(response.getBody());
    Assertions.assertEquals(10L, response.getBody().getId());
    }

    @Test
    public void getGateById_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(gateService.getGateById(88L)).thenReturn(Optional.empty());
        ResponseEntity<Gate> response = gateController.getGateById(88L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateGate_ReturnsUpdatedGate()
    {
        Gate updatedGate = new Gate();
        updatedGate.setId(88L);
        updatedGate.setGateNumber("Test number");

        Mockito.when(gateService.updateGate(eq(88L), any(Gate.class))).thenReturn(Optional.of(updatedGate));
        ResponseEntity<Gate> response = gateController.updateGate(88L, new Gate());

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(88L, response.getBody().getId());
    }

    @Test
    public void updateGate_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(gateService.updateGate(eq(65L), any(Gate.class))).thenReturn(Optional.empty());
        ResponseEntity<Gate> response = gateController.updateGate(65L, new Gate());

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteGate_ReturnsNothingWhenDeleted()
    {
        Mockito.when(gateService.deleteGateById(1L)).thenReturn(true);
        ResponseEntity<Void> response = gateController.deleteGate(1L);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void deleteGate_ReturnsNotFoundWhenMissing()
    {
        Mockito.when(gateService.deleteGateById(88L)).thenReturn(false);
        ResponseEntity<Void> response = gateController.deleteGate(88L);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

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

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class GateServiceTest
{
    @Mock
    private GateRepository gateRepository;

    @InjectMocks
    private GateService gateService;

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
    public void addNewGate_ReturnsNewGate()
    {
        Mockito.when(gateRepository.save(gate)).thenReturn(gate);
        Gate savedGate = gateService.addNewGate(gate);

        Assertions.assertEquals(gate, savedGate);
        verify(gateRepository).save(gate);
    }

    @Test
    public void returnFullGateList()
    {
        Mockito.when(gateRepository.findAll()).thenReturn(gateList);
        Iterable<Gate> expected = gateService.getAllGates();

        Assertions.assertEquals(gateList, expected);
    }

    @Test
    public void findGateById_ReturnsGateWhenFound()
    {
        Mockito.when(gateRepository.findById(1L)).thenReturn(Optional.of(gate));
        Optional<Gate> expected = gateService.getGateById(1L);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals(gate, expected.get());
    }

    @Test
    public void findGateById_ReturnsEmptyWhenMissing()
    {
        Mockito.when(gateRepository.findById(88L)).thenReturn(Optional.empty());
        Optional<Gate> expected = gateService.getGateById(88L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void updateGate_ReturnsUpdatedGate()
    {
        Gate updatedGate = new Gate("Test number");

        Mockito.when(gateRepository.findById(1L)).thenReturn(Optional.of(gate));
        Mockito.when(gateRepository.save(Mockito.any(Gate.class))).thenReturn(updatedGate);
        Optional<Gate> expected = gateService.updateGate(1L, updatedGate);

        Assertions.assertTrue(expected.isPresent());
        Assertions.assertEquals("Test number", expected.get().getGateNumber());
    }

    @Test
    public void updateGate_ReturnsGateNotFound()
    {
        Gate updatedGate = new Gate("Test number");

        Mockito.when(gateRepository.findById(88L)).thenReturn(Optional.empty());
        Optional<Gate> expected = gateService.updateGate(88L, updatedGate);

        Assertions.assertFalse(expected.isPresent());
    }

    @Test
    public void deleteGate_ReturnsTrueWhenDeleted()
    {
        Mockito.when(gateRepository.existsById(1L)).thenReturn(true);
        boolean deleted = gateService.deleteGateById(1L);

        Assertions.assertTrue(deleted);
        verify(gateRepository).deleteById(1L);
    }

    @Test
    public void deleteGate_ReturnsEmptyWhenMissing()
    {
        Mockito.when(gateRepository.findById(88L)).thenReturn(Optional.empty());
        Optional<Gate> expected = gateService.getGateById(88L);

        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    public void getGatesByAirport_ReturnsGatesWhenFound()
    {
        Mockito.when(gateRepository.findByAirportId(1L)).thenReturn(gateList);
        List<Gate> expected = gateService.getGatesByAirport(1L);

        Assertions.assertEquals(gateList, expected);
        verify(gateRepository).findByAirportId(1L);
    }

    @Test
    public void getGatesByAirport_ReturnsEmptyWhenMissing()
    {
        Mockito.when(gateRepository.findByAirportId(88L)).thenReturn(List.of());
        List<Gate> expected = gateService.getGatesByAirport(88L);

        Assertions.assertTrue(expected.isEmpty());
        verify(gateRepository).findByAirportId(88L);
    }
}

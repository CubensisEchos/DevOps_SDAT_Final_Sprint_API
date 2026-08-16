package com.keyin.flight;

import com.keyin.flight.dto.FlightResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FlightController {
    @Autowired
    private FlightService flightService;

    @PostMapping("/flight")
    public Flight addNewFlight(@RequestBody Flight flight) {
        return flightService.addNewFlight(flight);
    }

    @GetMapping("/flight")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/flight/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/flight/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return flightService.updateFlight(id, flight)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/flight/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        boolean deleted = flightService.deleteFlightById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/departures/{airportId}")
    public List<FlightResponseDto> getDepartures(@PathVariable Long airportId) {
        return flightService.getDepartures(airportId);
    }

    @GetMapping("/arrivals/{airportId}")
    public List<FlightResponseDto> getArrivals(@PathVariable Long airportId) {
        return flightService.getArrivals(airportId);
    }

    @GetMapping("/airline/{airlineId}")
    public List<Flight> getFlightsByAirline(@PathVariable Long airlineId)
    {
        return flightService.getFlightsByAirline(airlineId);
    }
}

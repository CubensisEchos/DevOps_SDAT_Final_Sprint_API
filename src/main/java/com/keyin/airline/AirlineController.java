package com.keyin.airline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AirlineController
{
    @Autowired
    private  AirlineService airlineService;

    @PostMapping("/airline")
    public Airline addNewAirline(@RequestBody Airline airline)
    {
        return airlineService.addNewAirline(airline);
    }

    @GetMapping("/airline")
    public ResponseEntity<List<Airline>> getAllAirlines()
    {
        return ResponseEntity.ok(airlineService.getAllAirlines());
    }

    @PutMapping("/airline/{id}")
    public ResponseEntity<Airline> getAirlineById(@PathVariable Long id)
    {
        return airlineService.getAirlineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/airline/{id}")
    public ResponseEntity<Airline> updateAirline(@PathVariable Long id, @RequestBody Airline airline)
    {
        return airlineService.updateAirline(id, airline)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/airline/{id}")
    public ResponseEntity<Void> deleteAirline(@PathVariable Long id)
    {
        boolean deleted = airlineService.deleteAirlineById(id);
        if (deleted)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

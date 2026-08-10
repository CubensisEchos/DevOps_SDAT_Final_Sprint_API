package com.keyin.flight;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService
{
    @Autowired
    private FlightRepository flightRepository;

    public Flight addNewFlight(Flight flight)
    {
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights()
    {
        return flightRepository.findAll();
    }

    public java.util.Optional<Flight> getFlightById(Long id)
    {
        return flightRepository.findById(id);
    }

    public java.util.Optional<Flight> updateFlight(Long id, Flight updatedFlight)
    {
        return flightRepository.findById(id).map(existing ->
        {
           existing.setFlightNumber(updatedFlight.getFlightNumber());
           existing.setScheduledArrival(updatedFlight.getScheduledArrival());
           existing.setScheduledDepartureTime(updatedFlight.getScheduledDepartureTime());
           existing.setStatus(updatedFlight.getStatus());
           return flightRepository.save(existing);
        });
    }

    public boolean deleteFlightById(Long id)
    {
        if (flightRepository.existsById(id))
        {
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

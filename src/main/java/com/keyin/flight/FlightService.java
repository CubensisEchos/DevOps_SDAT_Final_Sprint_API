package com.keyin.flight;

import com.keyin.aircraft.AircraftRepository;
import com.keyin.airline.AirlineRepository;
import com.keyin.airport.AirportRepository;
import com.keyin.gate.GateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService
{
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private GateRepository gateRepository;

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
           existing.setAirline(updatedFlight.getAirline());
           existing.setAircraft(updatedFlight.getAircraft());
           existing.setDepartureAirport(updatedFlight.getDepartureAirport());
           existing.setArrivalAirport(updatedFlight.getArrivalAirport());
           existing.setGate(updatedFlight.getGate());
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

    public List<Flight> getDepartures(Long airportId)
    {
        return flightRepository.findByDepartureAirportId(airportId);
    }

    public List<Flight> getArrivals(Long airportId)
    {
        return flightRepository.findByArrivalAirportId(airportId);
    }

    public List<Flight> getFlightsByAirline(Long airlineId)
    {
        return flightRepository.findByAirlineId(airlineId);
    }
}

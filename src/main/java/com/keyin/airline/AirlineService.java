package com.keyin.airline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirlineService
{
    @Autowired
    private AirlineRepository airlineRepository;

    public Airline addNewAirline(Airline airline)
    {
        return airlineRepository.save(airline);
    }

    public List<Airline> getAllAirlines()
    {
        return airlineRepository.findAll();
    }

    public java.util.Optional<Airline> getAirlineById(Long id)
    {
        return airlineRepository.findById(id);
    }

    public java.util.Optional<Airline> updateAirline(Long id, Airline updatedAirline)
    {
        return airlineRepository.findById(id).map(existing ->
        {
            existing.setAirlineName(updatedAirline.getAirlineName());
            existing.setAirlineCode(updatedAirline.getAirlineCode());
            existing.setAirlineCountry(updatedAirline.getAirlineCountry());
            return airlineRepository.save(existing);
        });
    }

    public boolean deleteAirlineById(Long id)
    {
        if (airlineRepository.existsById(id))
        {
            airlineRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

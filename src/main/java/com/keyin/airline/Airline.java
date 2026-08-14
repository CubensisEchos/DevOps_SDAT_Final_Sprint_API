package com.keyin.airline;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.keyin.aircraft.Aircraft;
import com.keyin.flight.Flight;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Airline
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String AirlineName;
    private String AirlineCode;
    private String AirlineCountry;

    @JsonManagedReference("airline-aircraft")
    @OneToMany(mappedBy = "airline")
    private List<Aircraft> aircraft = new ArrayList<>();

    @JsonManagedReference("airline-flight")
    @OneToMany(mappedBy = "airline")
    private List<Flight> flights = new ArrayList<>();

    public Airline()
    {

    }

    public Airline(String airlineName, String airlineCode, String airlineCountry)
    {
        AirlineName = airlineName;
        AirlineCode = airlineCode;
        AirlineCountry = airlineCountry;
    }

    public Airline(Long id, String airlineName, String airlineCode, String airlineCountry)
    {
        this.id = id;
        AirlineName = airlineName;
        AirlineCode = airlineCode;
        AirlineCountry = airlineCountry;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAirlineName()
    {
        return AirlineName;
    }

    public void setAirlineName(String airlineName)
    {
        AirlineName = airlineName;
    }

    public String getAirlineCode()
    {
        return AirlineCode;
    }

    public void setAirlineCode(String airlineCode)
    {
        AirlineCode = airlineCode;
    }

    public String getAirlineCountry()
    {
        return AirlineCountry;
    }

    public void setAirlineCountry(String airlineCountry)
    {
        AirlineCountry = airlineCountry;
    }

    public List<Aircraft> getAircraft()
    {
        return aircraft;
    }

    public void setAircraft(List<Aircraft> aircraft)
    {
        this.aircraft = aircraft;
    }

    public List<Flight> getFlights()
    {
        return flights;
    }

    public void setFlights(List<Flight> flights)
    {
        this.flights = flights;
    }
}

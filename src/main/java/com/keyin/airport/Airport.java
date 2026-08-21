package com.keyin.airport;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.keyin.flight.Flight;
import com.keyin.gate.Gate;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an airport entity in the system
 * It stores airport details and its associated gates, and flights
 */
@Entity
public class Airport
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String airportName;
    private String airportCode;
    private String city;
    private String province;
    private String Country;

    @JsonManagedReference("airport-gate")
    @OneToMany(mappedBy = "airport")
    private List<Gate> gates = new ArrayList<>();

    @JsonManagedReference("airport-departure-flight")
    @OneToMany(mappedBy = "departureAirport")
    private List<Flight> departures = new ArrayList<>();

    @JsonManagedReference("airport-arrival-flight")
    @OneToMany(mappedBy = "arrivalAirport")
    private List<Flight> arrivals = new ArrayList<>();

    public Airport()
    {

    }

    public Airport(String airportName, String airportCode, String city, String province, String country)
    {
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.city = city;
        this.province = province;
        Country = country;
    }

    public Airport(Long id, String airportName, String airportCode, String city, String province, String country)
    {
        this.id = id;
        this.airportName = airportName;
        this.airportCode = airportCode;
        this.city = city;
        this.province = province;
        Country = country;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAirportName()
    {
        return airportName;
    }

    public void setAirportName(String airportName)
    {
        this.airportName = airportName;
    }

    public String getAirportCode()
    {
        return airportCode;
    }

    public void setAirportCode(String airportCode)
    {
        this.airportCode = airportCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCountry()
    {
        return Country;
    }

    public void setCountry(String country)
    {
        Country = country;
    }

    public List<Gate> getGates()
    {
        return gates;
    }

    public void setGates(List<Gate> gates)
    {
        this.gates = gates;
    }

    public List<Flight> getDepartures()
    {
        return departures;
    }

    public void setDepartures(List<Flight> departures)
    {
        this.departures = departures;
    }

    public List<Flight> getArrivals()
    {
        return arrivals;
    }

    public void setArrivals(List<Flight> arrivals)
    {
        this.arrivals = arrivals;
    }
}

package com.keyin.aircraft;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.keyin.airline.Airline;
import com.keyin.flight.Flight;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Aircraft
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String AircraftName;
    private int numberOfPassengers;

    @JsonBackReference("airline-aircraft")
    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @JsonManagedReference("aircraft-flight")
    @OneToMany(mappedBy = "aircraft")
    private List<Flight> flights = new ArrayList<>();

    public Aircraft()
    {

    }

    public Aircraft(String aircraftName, int numberOfPassengers)
    {
        AircraftName = aircraftName;
        this.numberOfPassengers = numberOfPassengers;
    }

    public Aircraft(Long id, String aircraftName, int numberOfPassengers)
    {
        this.id = id;
        AircraftName = aircraftName;
        this.numberOfPassengers = numberOfPassengers;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAircraftName()
    {
        return AircraftName;
    }

    public void setAircraftName(String aircraftName)
    {
        AircraftName = aircraftName;
    }

    public int getNumberOfPassengers()
    {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers)
    {
        this.numberOfPassengers = numberOfPassengers;
    }

    public Airline getAirline()
    {
        return airline;
    }

    public void setAirline(Airline airline)
    {
        this.airline = airline;
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

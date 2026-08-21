package com.keyin.gate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.keyin.airport.Airport;
import com.keyin.flight.Flight;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a gate entity in the system
 * It stores gate details, such as gate number
 */
@Entity
public class Gate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gateNumber;

    @JsonBackReference("airport-gate")
    @ManyToOne
    @JoinColumn(name = "airport_id")
    private Airport airport;

    @JsonManagedReference("gate-flight")
    @OneToMany(mappedBy = "gate")
    private List<Flight> flights = new ArrayList<>();

    public Gate()
    {

    }

    public Gate(String gateNumber)
    {
        this.gateNumber = gateNumber;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getGateNumber()
    {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber)
    {
        this.gateNumber = gateNumber;
    }

    public Airport getAirport()
    {
        return airport;
    }

    public void setAirport(Airport airport)
    {
        this.airport = airport;
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

package com.keyin.flight;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.keyin.aircraft.Aircraft;
import com.keyin.airline.Airline;
import com.keyin.airport.Airport;
import com.keyin.enums.FlightStatus;
import com.keyin.gate.Gate;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents a flight entity in the system
 * It stores flight details such as status and schedule
 */
@Entity
public class Flight
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String flightNumber;
    private LocalDateTime scheduledDepartureTime;
    private LocalDateTime scheduledArrival;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @JsonBackReference("airline-flight")
    @ManyToOne
    @JoinColumn(name = "airline_id")
    private Airline airline;

    @JsonBackReference("aircraft-flight")
    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @JsonBackReference("airport-departure-flight")
    @ManyToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @JsonBackReference("airport-arrival-flight")
    @ManyToOne
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    @JsonBackReference("gate-flight")
    @ManyToOne
    @JoinColumn(name = "gate_id")
    private Gate gate;

    public Flight()
    {

    }

    public Flight(String flightNumber, LocalDateTime scheduledDepartureTime, LocalDateTime scheduledArrival, FlightStatus status)
    {
        this.flightNumber = flightNumber;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrival = scheduledArrival;
        this.status = status;
    }

    public Flight(Long id, String flightNumber, LocalDateTime scheduledDepartureTime, LocalDateTime scheduledArrival, FlightStatus status)
    {
        this.id = id;
        this.flightNumber = flightNumber;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrival = scheduledArrival;
        this.status = status;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber)
    {
        this.flightNumber = flightNumber;
    }

    public LocalDateTime getScheduledDepartureTime()
    {
        return scheduledDepartureTime;
    }

    public void setScheduledDepartureTime(LocalDateTime scheduledDepartureTime)
    {
        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    public LocalDateTime getScheduledArrival()
    {
        return scheduledArrival;
    }

    public void setScheduledArrival(LocalDateTime scheduledArrival)
    {
        this.scheduledArrival = scheduledArrival;
    }

    public FlightStatus getStatus()
    {
        return status;
    }

    public void setStatus(FlightStatus status)
    {
        this.status = status;
    }

    public Airline getAirline()
    {
        return airline;
    }

    public void setAirline(Airline airline)
    {
        this.airline = airline;
    }

    public Aircraft getAircraft()
    {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft)
    {
        this.aircraft = aircraft;
    }

    public Airport getDepartureAirport()
    {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport)
    {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport()
    {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport)
    {
        this.arrivalAirport = arrivalAirport;
    }

    public Gate getGate()
    {
        return gate;
    }

    public void setGate(Gate gate)
    {
        this.gate = gate;
    }
}

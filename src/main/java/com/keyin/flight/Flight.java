package com.keyin.flight;

import com.keyin.enums.FlightStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
}

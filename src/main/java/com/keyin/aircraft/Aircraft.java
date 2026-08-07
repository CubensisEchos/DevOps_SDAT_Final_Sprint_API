package com.keyin.aircraft;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aircraft
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String AircraftName;
    private int numberOfPassengers;

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
}

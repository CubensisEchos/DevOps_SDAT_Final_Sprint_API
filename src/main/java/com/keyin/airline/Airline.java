package com.keyin.airline;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Airline
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String AirlineName;
    private String AirlineCode;
    private String AirlineCountry;

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
}

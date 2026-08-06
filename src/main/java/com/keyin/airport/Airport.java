package com.keyin.airport;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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

    @Override
    public String toString()
    {
        return "airport{" +
                "id=" + id +
                ", airportName='" + airportName + '\'' +
                ", airportCode='" + airportCode + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", Country='" + Country + '\'' +
                '}';
    }
}

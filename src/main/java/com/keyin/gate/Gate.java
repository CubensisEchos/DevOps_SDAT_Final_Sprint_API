package com.keyin.gate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Gate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gateNumber;

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
}

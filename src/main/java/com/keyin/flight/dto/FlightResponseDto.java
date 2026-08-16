package com.keyin.flight.dto;

import com.keyin.flight.Flight;

public record FlightResponseDto(
        Long flightId,
        String flightNumber,
        String airlineName,
        String aircraftName,
        String gateNumber,
        String time
)
{
    public static FlightResponseDto from(Flight flight, String time)
    {
        return new FlightResponseDto(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getAirline() == null ? null : flight.getAirline().getAirlineName(),
                flight.getAircraft() == null ? null : flight.getAircraft().getAircraftName(),
                flight.getGate() == null ? null : flight.getGate().getGateNumber(),
                time
        );
    }
}
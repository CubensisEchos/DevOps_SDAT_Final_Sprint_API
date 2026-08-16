package com.keyin.flight.dto;

public record FlightRequestDto(
        String flightNumber,
        String scheduledDepartureTime,
        String scheduledArrival,
        String status,
        Long airlineId,
        Long aircraftId,
        Long departureAirportId,
        Long arrivalAirportId,
        Long gateId
)
{

}
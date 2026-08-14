package com.keyin.gate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GateRepository extends JpaRepository<Gate, Long>
{
    List<Gate> findByAirportId(Long airportId);
}

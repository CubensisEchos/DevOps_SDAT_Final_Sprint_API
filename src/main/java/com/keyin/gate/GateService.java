package com.keyin.gate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GateService
{
    @Autowired
    private GateRepository gateRepository;

    public Gate addNewGate(Gate gate)
    {
        return gateRepository.save(gate);
    }

    public List<Gate> getAllGates()
    {
        return gateRepository.findAll();
    }

    public java.util.Optional<Gate> getGateById(Long id)
    {
        return gateRepository.findById(id);
    }

    public java.util.Optional<Gate> updateGate(Long id, Gate updatedGate)
    {
        return gateRepository.findById(id).map(existing ->
        {
            existing.setGateNumber(updatedGate.getGateNumber());
            return gateRepository.save(existing);
        });
    }

    public boolean deleteGateById(Long id)
    {
        if (gateRepository.existsById(id))
        {
            gateRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

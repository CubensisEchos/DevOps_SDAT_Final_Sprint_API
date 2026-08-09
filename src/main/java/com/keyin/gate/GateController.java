package com.keyin.gate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class GateController
{
    @Autowired
    private GateService gateService;

    @PostMapping("/gate")
    public Gate createNewGate(@RequestBody Gate gate)
    {
        return gateService.addNewGate(gate);
    }

    @GetMapping("/gate")
    public ResponseEntity<List<Gate>> getAllGates()
    {
        return ResponseEntity.ok(gateService.getAllGates());
    }

    @GetMapping("/gate/{id}")
    public ResponseEntity<Gate> getGateById(@PathVariable Long id)
    {
        return gateService.getGateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/gate/{id}")
    public ResponseEntity<Gate> updateGate(@PathVariable Long id, @RequestBody Gate gate)
    {
        return gateService.updateGate(id, gate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/gate/{id}")
    public ResponseEntity<Void> deleteGate(@PathVariable Long id)
    {
        boolean deleted = gateService.deleteGateById(id);
        if (deleted)
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

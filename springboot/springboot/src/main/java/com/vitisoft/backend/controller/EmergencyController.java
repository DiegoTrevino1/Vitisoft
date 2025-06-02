package com.vitisoft.backend.controller;

import com.vitisoft.backend.db.DatabaseManager;
import com.vitisoft.backend.model.Emergency;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/emergency")
@CrossOrigin(origins = "*")
public class EmergencyController {

    @PostMapping("/add")
    public ResponseEntity<String> addEmergency(@RequestBody Emergency emergency) {
        DatabaseManager.insertEmergency(emergency);
        return ResponseEntity.ok("Emergency added successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Emergency>> getAllEmergencies() {
        List<Emergency> list = DatabaseManager.getAllEmergencies();
        return ResponseEntity.ok(list);
    }
}

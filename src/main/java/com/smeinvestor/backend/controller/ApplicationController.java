package com.smeinvestor.backend.controller;

import com.smeinvestor.backend.model.Application;
import com.smeinvestor.backend.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "*")
public class ApplicationController {
    
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<?> createApplication(@RequestBody Application application) {
        try {
            Application savedApplication = applicationService.createApplication(application);
            return ResponseEntity.status(201).body(savedApplication);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error creating application"));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllApplications() {
        try {
            List<Application> applications = applicationService.getAllApplications();
            return ResponseEntity.ok(applications);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Error fetching applications"));
        }
    }
}
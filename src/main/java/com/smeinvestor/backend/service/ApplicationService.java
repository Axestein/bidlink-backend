package com.smeinvestor.backend.service;

import com.smeinvestor.backend.model.Application;
import com.smeinvestor.backend.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Application> getApplicationById(String id) {
        return applicationRepository.findById(id);
    }
}
package com.smeinvestor.backend.repository;

import com.smeinvestor.backend.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findAllByOrderByCreatedAtDesc();
}
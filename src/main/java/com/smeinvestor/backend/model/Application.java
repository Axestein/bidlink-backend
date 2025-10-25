package com.smeinvestor.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document(collection = "applications")
public class Application {
    @Id
    private String id;
    
    private BigDecimal amount;
    private String purpose;
    private String status;
    private String companyName;
    private String ownerName;
    private Integer yearsInOperation;
    private BigDecimal annualRevenue;
    private String contactNumber;
    private String businessType;
    private String location;
    private LocalDateTime createdAt;

    public Application() {
        this.status = "Under Review";
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    
    public Integer getYearsInOperation() { return yearsInOperation; }
    public void setYearsInOperation(Integer yearsInOperation) { this.yearsInOperation = yearsInOperation; }
    
    public BigDecimal getAnnualRevenue() { return annualRevenue; }
    public void setAnnualRevenue(BigDecimal annualRevenue) { this.annualRevenue = annualRevenue; }
    
    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
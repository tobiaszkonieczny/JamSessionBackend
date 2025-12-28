package com.sap.jamsession.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public record Location(
    @Min(value = -90, message = "Latitude must be between -90 and 90 degrees.")
        @Max(value = 90, message = "Latitude must be between -90 and 90 degrees.")
        double latitude,
    @Min(value = -180, message = "Longitude must be between -180 and 180 degrees.")
        @Max(value = 180, message = "Longitude must be between -180 and 180 degrees.")
        double longitude) {}

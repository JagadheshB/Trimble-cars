package com.trimble.cars.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trimble.cars.enums.LeaseStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaseDto {
    @JsonProperty("lease_id")
    private Integer id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LeaseStatus status;
    private CarDto carDto;
}

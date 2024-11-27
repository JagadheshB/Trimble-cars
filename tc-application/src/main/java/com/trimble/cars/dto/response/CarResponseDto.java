package com.trimble.cars.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trimble.cars.dto.request.LeaseDto;
import com.trimble.cars.entity.Lease;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponseDto {
    @JsonProperty("carId")
    private Integer id;
    private String model;
    private String variant;
    private List<LeaseResponseDto> leaseDtoList;
}

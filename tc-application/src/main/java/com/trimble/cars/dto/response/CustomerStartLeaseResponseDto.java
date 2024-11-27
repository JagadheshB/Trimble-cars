package com.trimble.cars.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trimble.cars.dto.request.LeaseDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerStartLeaseResponseDto {
    @JsonProperty("customerId")
    private Integer id;
    @JsonProperty("customerName")
    private String name;
    private List<LeaseDto> leaseDtoList;
}

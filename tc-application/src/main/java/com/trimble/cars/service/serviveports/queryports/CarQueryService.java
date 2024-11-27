package com.trimble.cars.service.serviveports.queryports;

import com.trimble.cars.dto.response.CarResponseDto;
import com.trimble.cars.entity.Car;
import com.trimble.cars.enums.CarStatus;

import java.util.List;

public interface CarQueryService {
    CarResponseDto getCarStatusAndDetails(Integer carId);

    List<CarResponseDto> getCarsByStatus(CarStatus status);

}

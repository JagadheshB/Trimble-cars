package com.trimble.cars.service.serviveports.commandports;

import com.trimble.cars.dto.request.CreateOwnerRequestDto;
import com.trimble.cars.dto.request.RegisterCarRequestDto;
import com.trimble.cars.dto.request.UpdateOwnerDetailsRequestDto;
import com.trimble.cars.dto.response.CreateOwnerResponseDto;
import com.trimble.cars.dto.response.RegisterCarResponseDto;
import com.trimble.cars.dto.response.UpdateOwnerDetailsResponseDto;

public interface OwnerCommandService {

    CreateOwnerResponseDto createOwner(CreateOwnerRequestDto createOwnerRequestDto);

    RegisterCarResponseDto registerCar(RegisterCarRequestDto registerCarRequestDto);

    void deleteOwnerById(Integer ownerId);

    UpdateOwnerDetailsResponseDto updateOwnerById(UpdateOwnerDetailsRequestDto updateOwnerDetailsRequestDto);

    void deleteCarByOwnerIdAndCarId(Integer ownerId, Integer carId);

    RegisterCarResponseDto updateCarDetails(RegisterCarRequestDto registerCarRequestDto);
}

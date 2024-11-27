package com.trimble.cars.service.serviveports.queryports;

import com.trimble.cars.dto.request.LeaseDto;
import com.trimble.cars.dto.response.OwnerResponseDto;
import com.trimble.cars.entity.Owner;
import com.trimble.cars.enums.CarStatus;

import java.util.List;

public interface OwnerQueryService {
    OwnerResponseDto getOwnerById(Integer ownerId);

    List<OwnerResponseDto> getAllOwners();

    List<LeaseDto> getLeaseHistoryByOwnerId(Integer ownerId);

}

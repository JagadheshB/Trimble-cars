package com.trimble.cars.service.serviveports.queryports;

import com.trimble.cars.dto.request.LeaseDto;
import com.trimble.cars.entity.Owner;
import com.trimble.cars.enums.CarStatus;

import java.util.List;

public interface OwnerQueryService {
    Owner getCarStatusAndDetailsAndLeaseHistoryByOwnerId(Integer ownerId, CarStatus carStatus);

    // 2. Get an existing Owner by ID
    Owner getOwnerById(Integer ownerId);

    List<Owner> getAllOwners();

    List<LeaseDto> getLeaseHistoryByOwnerId(Integer ownerId);

//    List<OwnerCarLeaseResponseDto> getLeaseHistoryByOwnerId(Integer ownerId);
}

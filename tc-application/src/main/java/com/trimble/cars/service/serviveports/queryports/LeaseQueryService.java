package com.trimble.cars.service.serviveports.queryports;

import com.trimble.cars.dto.request.LeaseDto;
import com.trimble.cars.entity.Lease;
import com.trimble.cars.enums.LeaseStatus;

import java.util.List;

public interface LeaseQueryService {
    LeaseDto getLeaseDetailsById(Integer leaseId);

    List<LeaseDto> getLeaseDetailsByStatus(LeaseStatus status);

    List<LeaseDto> getAllLeaseDetails();
}

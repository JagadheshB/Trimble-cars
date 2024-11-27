package com.trimble.cars.service.serviveports.queryports;

import com.trimble.cars.dto.request.CustomerDto;
import com.trimble.cars.dto.request.LeaseDto;
import com.trimble.cars.entity.Car;
import com.trimble.cars.entity.Customer;
import com.trimble.cars.enums.CarStatus;
import com.trimble.cars.enums.LeaseStatus;

import java.util.List;

public interface CustomerQueryService {
    CustomerDto getCustomerById(Integer customerId);

    List<LeaseDto> getLeaseHistoryByCustomerId(Integer customerId);

    List<Car> viewCarsForLease(CarStatus status);

    List<CustomerDto> getAllCustomers();
}

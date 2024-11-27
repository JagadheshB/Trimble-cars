package com.trimble.cars.service.serviveports.commandports;

import com.trimble.cars.dto.request.CreateCustomerRequestDto;
import com.trimble.cars.dto.request.LeaseDto;
import com.trimble.cars.dto.request.UpdateCustomerDetailsRequestDto;
import com.trimble.cars.dto.request.UpdateOwnerDetailsRequestDto;
import com.trimble.cars.dto.response.CreateCustomerResponseDto;
import com.trimble.cars.dto.response.CustomerStartLeaseResponseDto;
import com.trimble.cars.dto.response.UpdateCustomerDetailsResponseDto;
import com.trimble.cars.dto.response.UpdateOwnerDetailsResponseDto;
import com.trimble.cars.entity.Customer;
import com.trimble.cars.entity.Lease;

public interface CustomerCommandService {
    CreateCustomerResponseDto registerCustomer(CreateCustomerRequestDto createCustomerRequestDto);

    void deleteCustomerById(Integer customerId);

    UpdateCustomerDetailsResponseDto updateCustomerById(UpdateCustomerDetailsRequestDto updateCustomerDetailsRequestDto);

    // 3. Start a lease
    CustomerStartLeaseResponseDto startLease(Integer customerId, Integer carId);

    // 4. End a lease
    LeaseDto endLease(Integer leaseId);
}

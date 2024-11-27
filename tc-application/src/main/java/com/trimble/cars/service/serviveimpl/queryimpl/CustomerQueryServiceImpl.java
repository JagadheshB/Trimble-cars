package com.trimble.cars.service.serviveimpl.queryimpl;

import com.trimble.cars.dto.request.CarDto;
import com.trimble.cars.dto.request.CustomerDto;
import com.trimble.cars.dto.request.LeaseDto;
import com.trimble.cars.entity.Car;
import com.trimble.cars.entity.Customer;
import com.trimble.cars.entity.Lease;
import com.trimble.cars.entity.Owner;
import com.trimble.cars.enums.CarStatus;
import com.trimble.cars.enums.LeaseStatus;
import com.trimble.cars.exception.ErrorException;
import com.trimble.cars.repository.queryrepository.CarQueryRepository;
import com.trimble.cars.repository.queryrepository.CustomerQueryRepository;
import com.trimble.cars.repository.queryrepository.LeaseQueryRepository;
import com.trimble.cars.service.serviveports.queryports.CustomerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerQueryServiceImpl implements CustomerQueryService {

    private final CustomerQueryRepository customerQueryRepository;
    private final CarQueryRepository carQueryRepository;
    private final LeaseQueryRepository leaseQueryRepository;

    @Override
    public CustomerDto getCustomerById(Integer customerId) {
        log.info("Received request to fetch customer with ID: {}", customerId);
        try {
            // Fetch the customer from the repository, or throw an exception if not found
            Customer customer = customerQueryRepository.findById(customerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("CustomerNotFound", "Customer not found with ID: ", customerId));

            // Log the customer details for debugging
            log.debug("Customer found: {} - {}", customer.getId(), customer.getName());

            // Map the Customer entity to CustomerDto and return the result
            return mapToCustomerDto(customer);
        } catch (ErrorException e) {
            log.error("Error occurred while retrieving customer with ID: {}. Error: {}", customerId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving customer with ID: {}. Error: {}", customerId, e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error while retrieving customer with ID: " + customerId);
        }
    }


    @Override
    public List<LeaseDto> getLeaseHistoryByCustomerId(Integer customerId) {
        log.info("Received request to fetch lease history for customer with ID: {}", customerId);
        try {
            Customer customer = customerQueryRepository.findById(customerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("CustomerNotFound", "Customer not found with ID: ", customerId));
            log.debug("Customer found: {} - {}", customer.getId(), customer.getName());
            List<LeaseDto> leaseDtos = mapLeasesToDtos(customer.getLeases());
            log.info("Total leases found for customer with ID {}: {}", customerId, leaseDtos.size());
            return leaseDtos;
        } catch (ErrorException e) {
            log.error("Error retrieving lease history for customer with ID: {}. Error: {}", customerId, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving lease history for customer with ID: {}. Error: {}", customerId, e.getMessage());
            throw ErrorException.internalError("InternalError", "Unexpected error while retrieving lease history for customer ID: " + customerId);
        }
    }
    private List<LeaseDto> mapLeasesToDtos(List<Lease> leases) {
        return leases.stream()
                .map(this::mapToLeaseDto)
                .collect(Collectors.toList());
    }
    private LeaseDto mapToLeaseDto(Lease lease) {
        CarDto carDto = new CarDto();
        carDto.setId(lease.getCar().getId());
        carDto.setModel(lease.getCar().getModel());
        carDto.setVariant(lease.getCar().getVariant());

        LeaseDto leaseDto = new LeaseDto();
        leaseDto.setId(lease.getId());
        leaseDto.setStartDate(lease.getStartDate());
        leaseDto.setEndDate(lease.getEndDate());
        leaseDto.setStatus(lease.getStatus());
        leaseDto.setCarDto(carDto);

        return leaseDto;
    }

    @Override
    public List<Car> viewCarsForLease(CarStatus status) {
        log.info("Received request to fetch cars for lease with status: {}", status);
        try {
            List<Car> cars = carQueryRepository.findByStatus(status.toString());
            if (cars.isEmpty()) {
                log.warn("No cars found for lease with status: {}", status);
                return Collections.emptyList();
            }
            log.info("Successfully fetched {} cars for lease with status: {}", cars.size(), status);
            return cars;
        } catch (Exception e) {
            log.error("Error retrieving cars for lease with status:{} ", status , e);
            throw ErrorException.internalError("InternalError", "Error retrieving cars for lease with status");
        }
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        log.info("Fetching all customers from the database.");
        try {
            List<Customer> customers = customerQueryRepository.findAll();
            if (customers.isEmpty()) {
                log.warn("No customers found in the database.");
            } else {
                log.info("Successfully fetched {} customers.", customers.size());
            }
            return customers.stream()
                    .map(this::mapToCustomerDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching customers from the database.", e);
            throw ErrorException.internalError("InternalError", "Error occurred while fetching customers from the database.");
        }
    }

    private CustomerDto mapToCustomerDto(Customer customer) {
        List<LeaseDto> leaseDtos = customer.getLeases().stream()
                .map(this::mapToLeaseGetAllDto)  // Map Lease to LeaseDto
                .collect(Collectors.toList());
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .leaseDtoList(leaseDtos)
                .build();
    }

    private LeaseDto mapToLeaseGetAllDto(Lease lease) {
        CarDto carDto = CarDto.builder()
                .id(lease.getCar().getId())
                .model(lease.getCar().getModel())
                .variant(lease.getCar().getVariant())
                .build();
        return LeaseDto.builder()
                .id(lease.getId())
                .startDate(lease.getStartDate())
                .endDate(lease.getEndDate())
                .status(lease.getStatus())
                .carDto(carDto)
                .build();
    }

}

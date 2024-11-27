package com.trimble.cars.service.serviveimpl.queryimpl;

import com.trimble.cars.dto.request.CarDto;
import com.trimble.cars.dto.request.LeaseDto;
import com.trimble.cars.entity.Car;
import com.trimble.cars.entity.Lease;
import com.trimble.cars.entity.Owner;
import com.trimble.cars.enums.CarStatus;
import com.trimble.cars.exception.ErrorException;
import com.trimble.cars.repository.queryrepository.CarQueryRepository;
import com.trimble.cars.repository.queryrepository.LeaseQueryRepository;
import com.trimble.cars.repository.queryrepository.OwnerQueryRepository;
import com.trimble.cars.service.serviveports.queryports.OwnerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerQueryServiceImpl implements OwnerQueryService {

    private final CarQueryRepository carQueryRepository;
    private final OwnerQueryRepository ownerQueryRepository;
    private final LeaseQueryRepository leaseQueryRepository;

    @Override
    public Owner getCarStatusAndDetailsAndLeaseHistoryByOwnerId(Integer ownerId, CarStatus carStatus) {
        log.info("Received request to fetch car details and lease history for owner with ID: {}", ownerId);
        try {
            Owner existingOwner = ownerQueryRepository.findById(ownerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("OwnerNotFound", "Owner not found with ID: ", ownerId));
            log.debug("Owner found: {} - {}", existingOwner.getId(), existingOwner.getName());
            List<Car> listOfCars;
            if (carStatus != null) {
                listOfCars = carQueryRepository.findByStatus(carStatus.toString());
                existingOwner.setCars(listOfCars);
                log.debug("Found {} cars with status: {}", listOfCars.size(), carStatus);
            } else {
                log.debug("No car status provided, returning all cars for the owner.");
            }
            log.info("Successfully fetched car details and lease history for owner with ID: {}", ownerId);
            return existingOwner;
        } catch (ErrorException e) {
            log.error("Error occurred while fetching car details and lease history for owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching car details and lease history for owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error while fetching car details and lease history for owner ID: " + ownerId);
        }
    }
    @Override
    public Owner getOwnerById(Integer ownerId) {
        log.info("Received request to fetch owner details for owner with ID: {}", ownerId);
        try {
            Owner owner = ownerQueryRepository.findById(ownerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("OwnerNotFound", "Owner not found with ID: ", ownerId));
            log.debug("Owner found: {} - {}", owner.getId(), owner.getName());
            return owner;
        } catch (ErrorException e) {
            log.error("Error occurred while fetching owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error while fetching owner with ID: " + ownerId);
        }
    }

    @Override
    public List<Owner> getAllOwners() {
        log.info("Received request to fetch all owners from the database.");
        try {
            List<Owner> owners = ownerQueryRepository.findAll();
            if (owners.isEmpty()) {
                log.warn("No owners found in the database.");
            } else {
                log.info("Successfully retrieved {} owners from the database.", owners.size());
            }
            return owners;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching owners. Error: {}", e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error occurred while fetching owners.");
        }
    }

    @Override
    public List<LeaseDto> getLeaseHistoryByOwnerId(Integer ownerId) {
        log.info("Received request to fetch lease history for owner with ID: {}", ownerId);
        try {
            Owner owner = ownerQueryRepository.findById(ownerId)
                    .orElseThrow(() -> ErrorException.resourceNotExist("OwnerNotFound", "Owner not found with ID: ", ownerId));
            log.debug("Owner found: {} - {}", owner.getId(), owner.getName());
            List<LeaseDto> leaseDtos = mapCarsToLeaseDtos(owner.getCars()); // Use the helper method
            log.info("Successfully retrieved {} leases for owner with ID: {}", leaseDtos.size(), ownerId);
            return leaseDtos;
        } catch (ErrorException e) {
            log.error("Error occurred while retrieving lease history for owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving lease history for owner with ID: {}. Error: {}", ownerId, e.getMessage(), e);
            throw ErrorException.internalError("InternalError", "Unexpected error while retrieving lease history for owner ID: " + ownerId);
        }
    }

    private List<LeaseDto> mapCarsToLeaseDtos(List<Car> cars) {
        return cars.stream()
                .flatMap(car -> car.getLeases().stream())
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
}

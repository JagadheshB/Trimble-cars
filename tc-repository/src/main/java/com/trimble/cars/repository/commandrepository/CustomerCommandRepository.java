package com.trimble.cars.repository.commandrepository;

import com.trimble.cars.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerCommandRepository extends JpaRepository<Customer, Integer> {
}

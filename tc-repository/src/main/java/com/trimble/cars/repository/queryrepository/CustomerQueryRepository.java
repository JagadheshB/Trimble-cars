package com.trimble.cars.repository.queryrepository;

import com.trimble.cars.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerQueryRepository extends JpaRepository<Customer, Integer> {
}

package com.trimble.cars.repository.commandrepository;

import com.trimble.cars.entity.Lease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaseCommandRepository extends JpaRepository<Lease, Integer> {
}

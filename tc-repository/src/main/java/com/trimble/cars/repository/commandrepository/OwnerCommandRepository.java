package com.trimble.cars.repository.commandrepository;

import com.trimble.cars.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerCommandRepository extends JpaRepository<Owner, Integer> {

}

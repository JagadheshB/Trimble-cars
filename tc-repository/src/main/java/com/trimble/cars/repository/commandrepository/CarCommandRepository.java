package com.trimble.cars.repository.commandrepository;

import com.trimble.cars.entity.Car;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarCommandRepository extends JpaRepository<Car, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Car c WHERE c.id = :carId")
    void deleteCarByCarId(@Param("carId") Integer carId);
}

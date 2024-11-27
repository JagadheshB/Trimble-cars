package com.trimble.cars.repository.queryrepository;

import com.trimble.cars.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarQueryRepository extends JpaRepository<Car, Integer> {
    @Query(value = "SELECT * FROM cars WHERE status = :status", nativeQuery = true)
    List<Car> findByStatus(@Param("status") String status);
}

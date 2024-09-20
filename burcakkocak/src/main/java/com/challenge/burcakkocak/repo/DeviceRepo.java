package com.challenge.burcakkocak.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.challenge.burcakkocak.entity.Device;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepo extends JpaRepository<Device, Long> {

    @Query("SELECT d FROM Device d WHERE d.brand = :brand")
    List<Device> findByBrand(String brand);
}

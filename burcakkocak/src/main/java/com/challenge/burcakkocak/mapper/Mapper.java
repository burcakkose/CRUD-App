package com.challenge.burcakkocak.mapper;

import com.challenge.burcakkocak.entity.dto.DeviceRequestDTO;
import com.challenge.burcakkocak.entity.dto.DeviceResponseDTO;
import com.challenge.burcakkocak.entity.Device;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Mapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Convert Entity to Response DTO
    public DeviceResponseDTO toResponseDTO(Device device){
        DeviceResponseDTO dto = new DeviceResponseDTO();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setBrand(device.getBrand());
        dto.setCreationTime(device.getCreationTime().format(FORMATTER)); // Convert LocalDateTime to String
        return dto;
    }

    // Convert Request DTO to Entity
    public Device toEntity(DeviceRequestDTO dto) {
        Device device = new Device();
        device.setName(dto.getName());
        device.setBrand(dto.getBrand());

        // Check if the creationTime is provided
        if (dto.getCreationTime() != null && !dto.getCreationTime().isEmpty()) {
            // Parse the string to LocalDateTime
            device.setCreationTime(LocalDateTime.parse(dto.getCreationTime(), FORMATTER));
        } else {
            // Set the current time if no creationTime is provided
            device.setCreationTime(LocalDateTime.now());
        }

        return device;
    }
}

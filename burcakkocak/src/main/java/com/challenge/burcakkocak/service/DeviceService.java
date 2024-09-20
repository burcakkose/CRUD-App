package com.challenge.burcakkocak.service;

import com.challenge.burcakkocak.entity.dto.DeviceRequestDTO;
import com.challenge.burcakkocak.entity.dto.DeviceResponseDTO;
import com.challenge.burcakkocak.entity.Device;
import com.challenge.burcakkocak.exception.ResourceNotFoundException;
import com.challenge.burcakkocak.repo.DeviceRepo;
import com.challenge.burcakkocak.mapper.Mapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DeviceService {

    private final DeviceRepo deviceRepo;
    private final Mapper deviceMapper;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);

    public DeviceService(DeviceRepo deviceRepo, Mapper deviceMapper) {
        this.deviceRepo = deviceRepo;
        this.deviceMapper = deviceMapper;
    }

    @Transactional
    public DeviceResponseDTO addDevice(DeviceRequestDTO dto) {
        logger.debug("Called addDevice(DeviceRequestDTO dto): {}", dto);

        Device device = deviceMapper.toEntity(dto);
        return deviceMapper.toResponseDTO(deviceRepo.save(device));
    }

    public DeviceResponseDTO getDeviceById(Long id) {
        logger.info("Called getDeviceById(Long id): {}", id);

        Device device = findDeviceById(id);
        return deviceMapper.toResponseDTO(device);
    }

    public List<DeviceResponseDTO> getAllDevices() {
        logger.info("Called getAllDevices()");

        List<Device> devices = deviceRepo.findAll();
        List<DeviceResponseDTO> responseDTOs = new ArrayList<DeviceResponseDTO>();
        for (Device device : devices) {
            responseDTOs.add(deviceMapper.toResponseDTO(device));
        }
        return responseDTOs;
    }

    @Transactional
    public DeviceResponseDTO updateDevice(Long id, DeviceRequestDTO dto) {
        logger.info("Called updateDevice(Long id, DeviceRequestDTO dto): {}", dto);

        Device device = findDeviceById(id);
        updateDeviceFields(device, dto, true);
        return deviceMapper.toResponseDTO(deviceRepo.save(device));
    }

    @Transactional
    public DeviceResponseDTO updateDevicePartially(Long id, DeviceRequestDTO dto) {
        logger.info("Called updateDevicePartially(Long id, DeviceRequestDTO dto): {}", dto);

        Device device = findDeviceById(id);
        updateDeviceFields(device, dto, false);
        return deviceMapper.toResponseDTO(deviceRepo.save(device));
    }

    @Transactional
    public void deleteDevice(Long id) {
        logger.info("Called deleteDevice(Long id): {}", id);

        findDeviceById(id); // Ensure the device exists before attempting to delete
        deviceRepo.deleteById(id);
    }

    public List<DeviceResponseDTO> searchDeviceByBrand(String brand) {
        logger.info("Called searchDeviceByBrand(String brand): {}", brand);

        List<Device> devices = deviceRepo.findByBrand(brand);
        List<DeviceResponseDTO> responseDTOs = new ArrayList<DeviceResponseDTO>();
        for (Device device : devices) {
            responseDTOs.add(deviceMapper.toResponseDTO(device));
        }
        return responseDTOs;
    }

    private Device findDeviceById(Long id) {
        logger.info("Called findDeviceById(Long id): {}", id);
        return deviceRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device not found with id: " + id));
    }

    private void updateDeviceFields(Device device, DeviceRequestDTO dto, boolean isFullUpdate) {
        logger.info("Called updateDeviceFields: {}", device);

        if (dto.getName() != null) {
            device.setName(dto.getName());
        }

        if (dto.getBrand() != null) {
            device.setBrand(dto.getBrand());
        }

        if (dto.getCreationTime() != null && !dto.getCreationTime().isEmpty()) {
            LocalDateTime parsedCreationTime = LocalDateTime.parse(dto.getCreationTime(), FORMATTER);
            device.setCreationTime(parsedCreationTime);
        } else if (isFullUpdate) {
            device.setCreationTime(LocalDateTime.now());
        }
    }
}
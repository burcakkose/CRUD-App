package com.challenge.burcakkocak.controller;

import com.challenge.burcakkocak.entity.dto.DeviceRequestDTO;
import com.challenge.burcakkocak.entity.dto.DeviceResponseDTO;
import com.challenge.burcakkocak.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;
    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    //1. Add Device
    @PostMapping
    public ResponseEntity<DeviceResponseDTO> addDevice(@Valid @RequestBody DeviceRequestDTO dto) {
        logger.info("Received POST request to add new device: {}", dto);
        DeviceResponseDTO responseDTO = deviceService.addDevice(dto);
        logger.info("Device added successfully with id: {}", responseDTO.getId());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    //2. Get device by ID
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> getDeviceById(@PathVariable Long id) {
        logger.info("Received GET request to get device with id: {}", id);
        if (deviceService.getDeviceById(id) != null) {
            logger.info("Found device with id: {}", id);
            return new ResponseEntity<>(deviceService.getDeviceById(id), HttpStatus.OK);
        } else {
            logger.warn("Device not found with id: {}", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //3. List all devices
    @GetMapping
    public ResponseEntity<List<DeviceResponseDTO>> getAllDevices() {
        logger.info("Received GET request to get all devices");
        try {
            final var devices = deviceService.getAllDevices();

            if (devices.isEmpty()) {
                logger.warn("No device found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("Devices were found");
            return new ResponseEntity<>(devices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 4. Update device fully
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> updateDevice(@PathVariable Long id,
                                                          @Valid @RequestBody DeviceRequestDTO dto) {
        logger.info("Received PUT request to update device with id: {}", id);
        return new ResponseEntity<>(deviceService.updateDevice(id, dto), HttpStatus.OK);
    }

    // 5. Update device partially
    @PatchMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> updateDevicePartially(@PathVariable Long id,
                                                                   @RequestBody DeviceRequestDTO dto) {
        logger.info("Received PATCH request to update device with id: {}", id);
        return new ResponseEntity<>(deviceService.updateDevicePartially(id, dto), HttpStatus.OK);
    }

    // 6. Delete device
    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable Long id) {
        logger.info("Received DELETE request with id: {}", id);
        deviceService.deleteDevice(id);
        logger.info("Device was deleted with id: {}", id);
    }

    // 7. Search device
    @GetMapping("/search")
    public ResponseEntity<List<DeviceResponseDTO>> searchDeviceByBrand(@RequestParam String brand) {
        logger.info("Received GET request to search device with brand: {}", brand);
        try {
            final var foundDevices = deviceService.searchDeviceByBrand(brand);

            if (foundDevices.isEmpty()) {
                logger.warn("No device found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("Devices were found");
            return new ResponseEntity<>(foundDevices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

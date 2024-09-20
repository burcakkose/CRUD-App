package com.challenge.burcakkocak.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.burcakkocak.entity.Device;
import com.challenge.burcakkocak.entity.dto.DeviceRequestDTO;
import com.challenge.burcakkocak.entity.dto.DeviceResponseDTO;
import com.challenge.burcakkocak.exception.ResourceNotFoundException;
import com.challenge.burcakkocak.repo.DeviceRepo;
import com.challenge.burcakkocak.mapper.Mapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class DeviceServiceTest {

    @Mock
    private DeviceRepo deviceRepo;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Should_ReturnDeviceResponseDTO_When_AddingNewDevice() {
        DeviceRequestDTO requestDTO = new DeviceRequestDTO();
        requestDTO.setName("Test Device");
        requestDTO.setBrand("Test Brand");
        requestDTO.setCreationTime("2024-09-19T10:00:00");

        Device device = new Device();
        device.setId(1L);
        device.setName("Test Device");
        device.setBrand("Test Brand");
        device.setCreationTime(LocalDateTime.parse("2024-09-19T10:00:00"));

        DeviceResponseDTO responseDTO = new DeviceResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test Device");
        responseDTO.setBrand("Test Brand");
        responseDTO.setCreationTime("2024-09-19T10:00:00");

        when(mapper.toEntity(requestDTO)).thenReturn(device);
        when(deviceRepo.save(device)).thenReturn(device);
        when(mapper.toResponseDTO(device)).thenReturn(responseDTO);

        DeviceResponseDTO result = deviceService.addDevice(requestDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Device", result.getName());
        assertEquals("Test Brand", result.getBrand());
        assertEquals("2024-09-19T10:00:00", result.getCreationTime());
    }

    @Test
    void When_GetDeviceById_Expect_DeviceResponseDTO() {
        Long deviceId = 1L;
        Device device = new Device();
        device.setId(deviceId);
        device.setName("Test Device");
        device.setBrand("Test Brand");
        device.setCreationTime(LocalDateTime.now());

        DeviceResponseDTO responseDTO = new DeviceResponseDTO();
        responseDTO.setId(deviceId);
        responseDTO.setName("Test Device");
        responseDTO.setBrand("Test Brand");
        responseDTO.setCreationTime(device.getCreationTime().toString());

        when(deviceRepo.findById(deviceId)).thenReturn(Optional.of(device));
        when(mapper.toResponseDTO(device)).thenReturn(responseDTO);

        DeviceResponseDTO result = deviceService.getDeviceById(deviceId);

        assertNotNull(result);
        assertEquals(deviceId, result.getId());
        assertEquals("Test Device", result.getName());
        assertEquals("Test Brand", result.getBrand());
    }

    @Test
    void Given_NonExistentDeviceId_When_GetDeviceById_Then_ThrowResourceNotFoundException() {
        Long nonExistentId = 999L;
        when(deviceRepo.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> deviceService.getDeviceById(nonExistentId));
    }

    @Test
    void Should_ReturnListOfDeviceResponseDTO_When_GetAllDevices() {
        List<Device> devices = Arrays.asList(
                new Device(1L, "Device 1", "Brand 1", LocalDateTime.now()),
                new Device(2L, "Device 2", "Brand 2", LocalDateTime.now())
        );

        List<DeviceResponseDTO> expectedResponseDTOs = Arrays.asList(
                new DeviceResponseDTO(),
                new DeviceResponseDTO()
        );

        when(deviceRepo.findAll()).thenReturn(devices);
        when(mapper.toResponseDTO(any(Device.class))).thenReturn(new DeviceResponseDTO());

        List<DeviceResponseDTO> result = deviceService.getAllDevices();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deviceRepo, times(1)).findAll();
        verify(mapper, times(2)).toResponseDTO(any(Device.class));
    }

    @Test
    void When_UpdateDevice_Expect_UpdatedDeviceResponseDTO() {
        Long deviceId = 1L;
        DeviceRequestDTO requestDTO = new DeviceRequestDTO();
        requestDTO.setName("Updated Device");
        requestDTO.setBrand("Updated Brand");
        requestDTO.setCreationTime("2024-09-19T11:00:00");

        Device existingDevice = new Device(deviceId, "Old Device", "Old Brand", LocalDateTime.now());
        Device updatedDevice = new Device(deviceId, "Updated Device", "Updated Brand", LocalDateTime.parse("2024-09-19T11:00:00"));

        DeviceResponseDTO responseDTO = new DeviceResponseDTO();
        responseDTO.setId(deviceId);
        responseDTO.setName("Updated Device");
        responseDTO.setBrand("Updated Brand");
        responseDTO.setCreationTime("2024-09-19T11:00:00");

        when(deviceRepo.findById(deviceId)).thenReturn(Optional.of(existingDevice));
        when(deviceRepo.save(any(Device.class))).thenReturn(updatedDevice);
        when(mapper.toResponseDTO(updatedDevice)).thenReturn(responseDTO);

        DeviceResponseDTO result = deviceService.updateDevice(deviceId, requestDTO);

        assertNotNull(result);
        assertEquals(deviceId, result.getId());
        assertEquals("Updated Device", result.getName());
        assertEquals("Updated Brand", result.getBrand());
        assertEquals("2024-09-19T11:00:00", result.getCreationTime());
    }

    @Test
    void Given_DeviceExists_When_DeleteDevice_Then_DeviceIsDeleted() {
        Long deviceId = 1L;
        Device device = new Device(deviceId, "Test Device", "Test Brand", LocalDateTime.now());

        when(deviceRepo.findById(deviceId)).thenReturn(Optional.of(device));

        deviceService.deleteDevice(deviceId);

        verify(deviceRepo, times(1)).findById(deviceId);
        verify(deviceRepo, times(1)).deleteById(deviceId);
    }

    @Test
    void Should_ReturnListOfDeviceResponseDTO_When_SearchDeviceByBrand() {
        String brand = "Test Brand";
        List<Device> devices = Arrays.asList(
                new Device(1L, "Device 1", brand, LocalDateTime.now()),
                new Device(2L, "Device 2", brand, LocalDateTime.now())
        );

        List<DeviceResponseDTO> expectedResponseDTOs = Arrays.asList(
                new DeviceResponseDTO(),
                new DeviceResponseDTO()
        );

        when(deviceRepo.findByBrand(brand)).thenReturn(devices);
        when(mapper.toResponseDTO(any(Device.class))).thenReturn(new DeviceResponseDTO());

        List<DeviceResponseDTO> result = deviceService.searchDeviceByBrand(brand);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deviceRepo, times(1)).findByBrand(brand);
        verify(mapper, times(2)).toResponseDTO(any(Device.class));
    }
}
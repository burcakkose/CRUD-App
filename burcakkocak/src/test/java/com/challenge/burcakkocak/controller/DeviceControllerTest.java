package com.challenge.burcakkocak.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.challenge.burcakkocak.entity.dto.DeviceRequestDTO;
import com.challenge.burcakkocak.entity.dto.DeviceResponseDTO;
import com.challenge.burcakkocak.service.DeviceService;

import java.util.Arrays;
import java.util.List;

class DeviceControllerTest {

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceController deviceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Given_ValidDeviceRequestDTO_When_AddDevice_Then_ReturnCreatedStatus() {
        DeviceRequestDTO requestDTO = new DeviceRequestDTO();
        requestDTO.setName("Test Device");
        requestDTO.setBrand("Test Brand");
        requestDTO.setCreationTime("2024-09-19T10:00:00");

        DeviceResponseDTO responseDTO = new DeviceResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test Device");
        responseDTO.setBrand("Test Brand");
        responseDTO.setCreationTime("2024-09-19T10:00:00");

        when(deviceService.addDevice(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<DeviceResponseDTO> response = deviceController.addDevice(requestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void Should_ReturnDeviceResponseDTO_When_GetDeviceById() {
        Long deviceId = 1L;
        DeviceResponseDTO responseDTO = new DeviceResponseDTO();
        responseDTO.setId(deviceId);
        responseDTO.setName("Test Device");
        responseDTO.setBrand("Test Brand");
        responseDTO.setCreationTime("2024-09-19T10:00:00");

        when(deviceService.getDeviceById(deviceId)).thenReturn(responseDTO);

        ResponseEntity<DeviceResponseDTO> response = deviceController.getDeviceById(deviceId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(deviceId, response.getBody().getId());
    }

    @Test
    void When_GetAllDevices_Expect_ListOfDeviceResponseDTO() {
        List<DeviceResponseDTO> devices = Arrays.asList(
                new DeviceResponseDTO(),
                new DeviceResponseDTO()
        );

        when(deviceService.getAllDevices()).thenReturn(devices);

        ResponseEntity<List<DeviceResponseDTO>> response = deviceController.getAllDevices();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void Given_NoDevices_When_GetAllDevices_Then_ReturnNoContent() {
        when(deviceService.getAllDevices()).thenReturn(Arrays.asList());

        ResponseEntity<List<DeviceResponseDTO>> response = deviceController.getAllDevices();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void Should_ReturnUpdatedDeviceResponseDTO_When_UpdateDevice() {
        Long deviceId = 1L;
        DeviceRequestDTO requestDTO = new DeviceRequestDTO();
        requestDTO.setName("Updated Device");
        requestDTO.setBrand("Updated Brand");
        requestDTO.setCreationTime("2024-09-19T11:00:00");

        DeviceResponseDTO responseDTO = new DeviceResponseDTO();
        responseDTO.setId(deviceId);
        responseDTO.setName("Updated Device");
        responseDTO.setBrand("Updated Brand");
        responseDTO.setCreationTime("2024-09-19T11:00:00");

        when(deviceService.updateDevice(deviceId, requestDTO)).thenReturn(responseDTO);

        ResponseEntity<DeviceResponseDTO> response = deviceController.updateDevice(deviceId, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Device", response.getBody().getName());
    }

    @Test
    void When_UpdateDevicePartially_Expect_UpdatedDeviceResponseDTO() {
        Long deviceId = 1L;
        DeviceRequestDTO requestDTO = new DeviceRequestDTO();
        requestDTO.setName("Partially Updated Device");

        DeviceResponseDTO responseDTO = new DeviceResponseDTO();
        responseDTO.setId(deviceId);
        responseDTO.setName("Partially Updated Device");
        responseDTO.setBrand("Existing Brand");
        responseDTO.setCreationTime("2024-09-19T10:00:00");

        when(deviceService.updateDevicePartially(deviceId, requestDTO)).thenReturn(responseDTO);

        ResponseEntity<DeviceResponseDTO> response = deviceController.updateDevicePartially(deviceId, requestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Partially Updated Device", response.getBody().getName());
    }

    @Test
    void Given_DeviceExists_When_DeleteDevice_Then_MethodIsCalled() {
        Long deviceId = 1L;

        deviceController.deleteDevice(deviceId);

        verify(deviceService, times(1)).deleteDevice(deviceId);
    }

    @Test
    void Should_ReturnListOfDeviceResponseDTO_When_SearchDeviceByBrand() {
        String brand = "Test Brand";
        List<DeviceResponseDTO> devices = Arrays.asList(
                new DeviceResponseDTO(),
                new DeviceResponseDTO()
        );

        when(deviceService.searchDeviceByBrand(brand)).thenReturn(devices);

        ResponseEntity<List<DeviceResponseDTO>> response = deviceController.searchDeviceByBrand(brand);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void Given_NoDevicesFound_When_SearchDeviceByBrand_Then_ReturnNoContent() {
        String brand = "Non-existent Brand";
        when(deviceService.searchDeviceByBrand(brand)).thenReturn(Arrays.asList());

        ResponseEntity<List<DeviceResponseDTO>> response = deviceController.searchDeviceByBrand(brand);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
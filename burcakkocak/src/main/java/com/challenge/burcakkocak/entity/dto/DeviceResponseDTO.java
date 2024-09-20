package com.challenge.burcakkocak.entity.dto;

import lombok.Data;

@Data
public class DeviceResponseDTO {
    private Long id;
    private String name;
    private String brand;
    private String creationTime; // Output formatted
}

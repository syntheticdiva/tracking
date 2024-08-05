package com.example.tracking.dto.postOfficeDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostOfficeResponseDTO {
    private Long id; // Поле id, которое будет отображаться в ответе
    private String index;
    private String name;
    private String address;

    public PostOfficeResponseDTO(String number, String mainPostOffice, String s) {
    }

    public PostOfficeResponseDTO(Long id, String index, String name, String address) {
        this.id = id;
        this.index = index;
        this.name = name;
        this.address = address;
    }
}
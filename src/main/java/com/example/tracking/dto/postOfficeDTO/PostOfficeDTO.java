package com.example.tracking.dto.postOfficeDTO;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PostOfficeDTO {
    private String index;
    private String name;
    private String address;

    public PostOfficeDTO() {
    }

    public PostOfficeDTO(String index, String name, String address) {
        this.index = index;
        this.name = name;
        this.address = address;
    }
}

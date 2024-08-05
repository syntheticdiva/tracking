package com.example.tracking.dto.postOfficeDTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostOfficeRequestDTO {

    @NotBlank(message = "Индекс не может быть пустым")
    @Pattern(regexp = "\\d{6}", message = "Индекс должен содержать ровно 6 цифр")
    private String index;

    @NotBlank(message = "Название почтового отделения не может быть пустым")
    private String name;

    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    public PostOfficeRequestDTO() {
    }

    public PostOfficeRequestDTO(String index, String name, String address) {
        this.index = index;
        this.name = name;
        this.address = address;
    }
}
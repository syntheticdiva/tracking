package com.example.tracking.dto.mailItemDTO;

import com.example.tracking.enums.MailItemType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class MailItemRequestDTO {

    @NotBlank(message = "Имя получателя не может быть пустым")
    private String recipientName;

    private MailItemType type; // Поле типа MailItemType

    @NotBlank(message = "Индекс получателя не может быть пустым")
    @Pattern(regexp = "\\d{6}", message = "Индекс получателя должен содержать ровно 6 цифр")
    private String recipientIndex;

    @NotBlank(message = "Адрес получателя не может быть пустым")
    private String recipientAddress;

    // Конструктор с параметрами
    @JsonCreator
    public MailItemRequestDTO(@JsonProperty("recipientName") String recipientName,
                              @JsonProperty("type") MailItemType type,
                              @JsonProperty("recipientIndex") String recipientIndex,
                              @JsonProperty("recipientAddress") String recipientAddress) {
        this.recipientName = recipientName;
        this.type = type;
        this.recipientIndex = recipientIndex;
        this.recipientAddress = recipientAddress;
    }
}
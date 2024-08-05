package com.example.tracking.dto.mailItemDTO;

import com.example.tracking.enums.MailItemType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class MailItemResponseDTO {
    private Long id;
    private String recipientName;
    private MailItemType type; // Поле типа MailItemType
    private String recipientIndex;
    private String recipientAddress;
    private String status;
    private Date createdAt;

    // Конструктор с параметрами
    public MailItemResponseDTO(Long id, String recipientName, MailItemType type, String recipientIndex, String recipientAddress, String status, Date createdAt) {
        this.id = id;
        this.recipientName = recipientName;
        this.type = type;
        this.recipientIndex = recipientIndex;
        this.recipientAddress = recipientAddress;
        this.status = status;
        this.createdAt = createdAt;
    }
}
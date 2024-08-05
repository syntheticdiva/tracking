package com.example.tracking.dto.mailItemDTO;


import com.example.tracking.enums.MailItemType;
import com.example.tracking.model.MailItem;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class MailItemDTO {
    private Long id;

    private String recipientName;

    private MailItemType type; // Поле типа MailItemType

    private String recipientIndex;

    private String recipientAddress;

    private String status;

    private Date createdAt;

    // Конструктор по умолчанию
    public MailItemDTO(Long id, String recipientName, String name, String recipientIndex, String recipientAddress, String named, Date createdAt) {
    }

    // Конструктор, который принимает MailItem
    public MailItemDTO(MailItem mailItem) {
        this.id = mailItem.getId();
        this.recipientName = mailItem.getRecipientName();
        this.type = mailItem.getType(); // Получаем MailItemType
        this.recipientIndex = mailItem.getRecipientIndex();
        this.recipientAddress = mailItem.getRecipientAddress();
        this.status = mailItem.getStatus().name(); // Если status - это перечисление, используйте name()
        this.createdAt = mailItem.getCreatedAt();
    }

    // Конструктор с параметрами
    @JsonCreator
    public MailItemDTO(@JsonProperty("id") Long id,
                       @JsonProperty("recipientName") String recipientName,
                       @JsonProperty("type") MailItemType type,
                       @JsonProperty("recipientIndex") String recipientIndex,
                       @JsonProperty("recipientAddress") String recipientAddress,
                       @JsonProperty("status") String status,
                       @JsonProperty("createdAt") Date createdAt) {
        this.id = id;
        this.recipientName = recipientName;
        this.type = type; // Устанавливаем MailItemType
        this.recipientIndex = recipientIndex;
        this.recipientAddress = recipientAddress;
        this.status = status;
        this.createdAt = createdAt;
    }
}

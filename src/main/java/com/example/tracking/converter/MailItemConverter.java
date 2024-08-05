package com.example.tracking.converter;

import com.example.tracking.dto.mailItemDTO.MailItemDTO;
import com.example.tracking.enums.MailItemStatus;
import com.example.tracking.enums.MailItemType;
import com.example.tracking.model.MailItem;
import org.springframework.stereotype.Component;


@Component
public class MailItemConverter {
    public static MailItemDTO toDTO(MailItem mailItem) {
        if (mailItem == null) {
            return null;
        }
        return new MailItemDTO(
                mailItem.getId(),
                mailItem.getRecipientName(),
                mailItem.getType().name(), // Преобразуем перечисление в строку
                mailItem.getRecipientIndex(),
                mailItem.getRecipientAddress(),
                mailItem.getStatus().name(), // Преобразуем перечисление в строку
                mailItem.getCreatedAt()
        );
    }

    public static MailItem toEntity(MailItemDTO mailItemDTO) {
        if (mailItemDTO == null) {
            return null;
        }
        MailItem mailItem = new MailItem();
        mailItem.setId(mailItemDTO.getId());
        mailItem.setRecipientName(mailItemDTO.getRecipientName());
        mailItem.setType(MailItemType.valueOf(String.valueOf(mailItemDTO.getType()))); // Преобразуем строку в перечисление
        mailItem.setRecipientIndex(mailItemDTO.getRecipientIndex());
        mailItem.setRecipientAddress(mailItemDTO.getRecipientAddress());
        mailItem.setStatus(MailItemStatus.valueOf(mailItemDTO.getStatus())); // Преобразуем строку в перечисление
        mailItem.setCreatedAt(mailItemDTO.getCreatedAt());
        return mailItem;
    }
}
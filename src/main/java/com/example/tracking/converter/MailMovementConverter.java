package com.example.tracking.converter;

import com.example.tracking.dto.MailMovementDTO;
import com.example.tracking.enums.MailMovementAction;
import com.example.tracking.model.MailItem;
import com.example.tracking.model.MailMovement;
import com.example.tracking.model.PostOffice;
import org.springframework.stereotype.Component;

@Component
public class MailMovementConverter {
    public MailMovementDTO toDTO(MailMovement mailMovement) {
        MailMovementDTO dto = new MailMovementDTO();
        dto.setId(mailMovement.getId());
        dto.setMailItemId(mailMovement.getMailItem().getId());
        dto.setPostOfficeId(mailMovement.getPostOffice().getId());
        dto.setMovementDate(mailMovement.getMovementDate());
        dto.setAction(mailMovement.getAction().name());
        return dto;
    }

    public static MailMovement fromDTO(MailMovementDTO dto) {
        MailMovement mailMovement = new MailMovement();
        mailMovement.setId(dto.getId());

        MailItem mailItem = new MailItem();
        mailItem.setId(dto.getMailItemId());
        mailMovement.setMailItem(mailItem);

        PostOffice postOffice = new PostOffice();
        postOffice.setId(dto.getPostOfficeId());
        mailMovement.setPostOffice(postOffice);

        mailMovement.setMovementDate(dto.getMovementDate());
        mailMovement.setAction(MailMovementAction.valueOf(dto.getAction()));
        return mailMovement;
    }
}
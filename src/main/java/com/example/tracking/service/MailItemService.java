package com.example.tracking.service;

import com.example.tracking.dto.MailMovementDTO;
import com.example.tracking.dto.mailItemDTO.MailItemDTO;
import com.example.tracking.dto.mailItemDTO.MailItemRequestDTO;
import com.example.tracking.dto.mailItemDTO.MailItemResponseDTO;
import com.example.tracking.enums.MailItemStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MailItemService {

    MailItemResponseDTO registerMailItem(MailItemRequestDTO mailItemRequestDTO);
    MailMovementDTO addArrival(Long mailItemId, Long postOfficeId);
    MailMovementDTO addDeparture(Long mailItemId, Long postOfficeId);
    MailMovementDTO markAsDelivered(Long mailItemId, Long postOfficeId);
    void updateMailItemStatus(Long mailItemId, MailItemStatus newStatus);
    MailItemDTO getMailItemStatus(Long mailItemId);
    List<MailMovementDTO> getMailHistory(Long mailItemId);
}

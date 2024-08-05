package com.example.tracking.service.impl;

import com.example.tracking.converter.MailMovementConverter;
import com.example.tracking.dto.mailItemDTO.MailItemDTO;
import com.example.tracking.dto.MailMovementDTO;
import com.example.tracking.dto.mailItemDTO.MailItemRequestDTO;
import com.example.tracking.dto.mailItemDTO.MailItemResponseDTO;
import com.example.tracking.enums.MailItemStatus;
import com.example.tracking.enums.MailMovementAction;
import com.example.tracking.exception.MailItemNotFoundException;
import com.example.tracking.model.MailItem;

import com.example.tracking.model.MailMovement;
import com.example.tracking.model.PostOffice;

import com.example.tracking.repository.MailItemRepository;
import com.example.tracking.repository.MailMovementRepository;
import com.example.tracking.repository.PostOfficeRepository;
import com.example.tracking.service.MailItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class MailItemServiceImpl implements MailItemService {
    @Autowired
    private MailItemRepository mailItemRepository;

    @Autowired
    private MailMovementRepository mailMovementRepository;

    @Autowired
    private PostOfficeRepository postOfficeRepository;

    @Autowired
    private MailMovementConverter mailMovementMapper;

    // Регистрация почтового отправления
    public MailItemResponseDTO registerMailItem(@Valid MailItemRequestDTO mailItemRequestDTO) {
        log.info("Регистрация почтового отправления для получателя: {}", mailItemRequestDTO.getRecipientName());

        MailItem mailItem = new MailItem();
        mailItem.setCreatedAt(new Date());
        mailItem.setStatus(MailItemStatus.REGISTERED);
        mailItem.setType(mailItemRequestDTO.getType());
        mailItem.setRecipientName(mailItemRequestDTO.getRecipientName());
        mailItem.setRecipientAddress(mailItemRequestDTO.getRecipientAddress());
        mailItem.setRecipientIndex(mailItemRequestDTO.getRecipientIndex());

        mailItem = mailItemRepository.save(mailItem);
        log.info("Почтовое отправление успешно зарегистрировано с ID: {}", mailItem.getId());

        return new MailItemResponseDTO(
                mailItem.getId(),
                mailItem.getRecipientName(),
                mailItem.getType(),
                mailItem.getRecipientIndex(),
                mailItem.getRecipientAddress(),
                mailItem.getStatus().name(),
                mailItem.getCreatedAt()
        );
    }
    // Прибытие в промежуточное почтовое отделение
    public MailMovementDTO addArrival(Long mailItemId, Long postOfficeId) {
        log.info("Добавление прибытия для почтового отправления ID: {}", mailItemId);

        MailItem mailItem = mailItemRepository.findById(mailItemId)
                .orElseThrow(() -> new MailItemNotFoundException("Почтовое отправление не найдено с ID: " + mailItemId));
        PostOffice postOffice = postOfficeRepository.findById(String.valueOf(postOfficeId))
                .orElseThrow(() -> new IllegalArgumentException("Почтовое отделение не найдено"));


        MailMovement movement = new MailMovement();
        movement.setMailItem(mailItem);
        movement.setPostOffice(postOffice);
        movement.setMovementDate(new Date());
        movement.setAction(MailMovementAction.ARRIVAL);
        movement.setStatus(MailItemStatus.ACCEPTED);

        mailItem.setStatus(MailItemStatus.ACCEPTED);
        mailItemRepository.save(mailItem);

        MailMovement savedMovement = mailMovementRepository.save(movement);
        log.info("Прибытие успешно добавлено для почтового отправления ID: {}", mailItemId);

        // Логирование перед обращением к мапперу
        log.info("Преобразование MailMovement в DTO для ID: {}", savedMovement.getId());
        MailMovementDTO movementDTO = mailMovementMapper.toDTO(savedMovement);
        // Логирование после обращения к мапперу
        log.info("Преобразование завершено для ID: {}", movementDTO.getId());

        return movementDTO;
    }

    public MailMovementDTO addDeparture(Long mailItemId, Long postOfficeId) {
        log.info("Добавление отправления для почтового отправления ID: {}", mailItemId);

        MailItem mailItem = mailItemRepository.findById(mailItemId)
                .orElseThrow(() -> new MailItemNotFoundException("Почтовое отправление не найдено с ID: " + mailItemId));
        PostOffice postOffice = postOfficeRepository.findById(String.valueOf(postOfficeId))
                .orElseThrow(() -> new IllegalArgumentException("Почтовое отделение не найдено"));

        MailMovement movement = new MailMovement();
        movement.setMailItem(mailItem);
        movement.setPostOffice(postOffice);
        movement.setMovementDate(new Date());
        movement.setAction(MailMovementAction.DEPARTURE);
        movement.setStatus(MailItemStatus.IN_TRANSIT);

        mailItem.setStatus(MailItemStatus.IN_TRANSIT);
        mailItemRepository.save(mailItem);

        MailMovement savedMovement = mailMovementRepository.save(movement);

        // Проверка, что savedMovement не null
        if (savedMovement == null) {
            log.error("Не удалось сохранить движение почтового отправления");
            throw new RuntimeException("Не удалось сохранить движение почтового отправления");
        }

        log.info("Отправление успешно добавлено для почтового отправления ID: {}", mailItemId);

        // Логирование перед обращением к мапперу
        log.info("Преобразование MailMovement в DTO для ID: {}", savedMovement.getId());
        MailMovementDTO movementDTO = mailMovementMapper.toDTO(savedMovement);
        // Логирование после обращения к мапперу
        log.info("Преобразование завершено для ID: {}", movementDTO.getId());

        return movementDTO;
    }
    // Получение адресатом
    public MailMovementDTO markAsDelivered(Long mailItemId, Long postOfficeId) {
        log.info("Отметка почтового отправления ID: {} как доставленного", mailItemId);

        MailItem mailItem = mailItemRepository.findById(mailItemId)
                .orElseThrow(() -> new MailItemNotFoundException("Почтовое отправление не найдено с ID: " + mailItemId));
        PostOffice postOffice = postOfficeRepository.findById(String.valueOf(postOfficeId))
                .orElseThrow(() -> new IllegalArgumentException("Почтовое отделение не найдено"));


        MailMovement movement = new MailMovement();
        movement.setMailItem(mailItem);
        movement.setPostOffice(postOffice);
        movement.setMovementDate(new Date());
        movement.setAction(MailMovementAction.DELIVERY);
        movement.setStatus(MailItemStatus.DELIVERED);

        mailItem.setStatus(MailItemStatus.DELIVERED);
        mailItemRepository.save(mailItem);

        MailMovement savedMovement = mailMovementRepository.save(movement);
        log.info("Почтовое отправление ID: {} отмечено как доставленное", mailItemId);

        // Логирование перед обращением к мапперу
        log.info("Преобразование MailMovement в DTO для ID: {}", savedMovement.getId());
        MailMovementDTO movementDTO = mailMovementMapper.toDTO(savedMovement);
        // Логирование после обращения к мапперу
        log.info("Преобразование завершено для ID: {}", movementDTO.getId());

        return movementDTO;
    }

    // Обновление статуса почтового отправления
    public void updateMailItemStatus(Long mailItemId, MailItemStatus newStatus) {
        log.info("Обновление статуса почтового отправления ID: {} на {}", mailItemId, newStatus);

        MailItem mailItem = mailItemRepository.findById(mailItemId)
                .orElseThrow(() -> new IllegalArgumentException("Почтовое отправление не найдено"));

        mailItem.setStatus(newStatus);
        mailItemRepository.save(mailItem);
        log.info("Статус почтового отправления ID: {} обновлён на {}", mailItemId, newStatus);
    }

    // Просмотр статуса почтового отправления
    public MailItemDTO getMailItemStatus(Long mailItemId) {
        log.info("Получение статуса почтового отправления ID: {}", mailItemId);

        MailItem mailItem = mailItemRepository.findById(mailItemId)
                .orElseThrow(() -> new MailItemNotFoundException("Почтовое отправление не найдено с ID: " + mailItemId));
        return new MailItemDTO(mailItem);
    }

    // Получение полной истории передвижения
    public List<MailMovementDTO> getMailHistory(Long mailItemId) {
        log.info("Получение истории почтового отправления ID: {}", mailItemId);

        MailItem mailItem = mailItemRepository.findById(mailItemId)
                .orElseThrow(() -> new MailItemNotFoundException("Почтовое отправление не найдено с ID: " + mailItemId));
        List<MailMovement> movements = mailMovementRepository.findByMailItem(mailItem);
        return movements.stream()
                .map(mailMovementMapper::toDTO)
                .toList();
    }
}
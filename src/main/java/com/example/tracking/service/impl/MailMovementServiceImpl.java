package com.example.tracking.service.impl;

import com.example.tracking.model.MailItem;
import com.example.tracking.model.MailMovement;
import com.example.tracking.model.PostOffice;
import com.example.tracking.repository.MailItemRepository;
import com.example.tracking.repository.MailMovementRepository;
import com.example.tracking.repository.PostOfficeRepository;
import com.example.tracking.service.MailMovementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Slf4j
@Service
public class MailMovementServiceImpl implements MailMovementService {
    @Autowired
    private MailMovementRepository mailMovementRepository;

    @Autowired
    private PostOfficeRepository postOfficeRepository;

    @Autowired
    private MailItemRepository mailItemRepository;

    public MailMovement saveMailMovement(MailMovement mailMovement) {
        log.info("Сохранение почтового движения: {}", mailMovement);

        // Проверка и сохранение почтового отделения
        PostOffice postOffice = mailMovement.getPostOffice();
        if (postOffice == null || postOffice.getId() == null) {
            log.info("Почтовое отделение новое, сохраняем его.");
            postOffice = postOfficeRepository.save(postOffice);
        } else {
            log.info("Проверяем существующее почтовое отделение с ID: {}", postOffice.getId());
            String postOfficeId = String.valueOf(postOffice.getId());
            postOffice = postOfficeRepository.findById(postOfficeId)
                    .orElseThrow(() -> {
                        log.error("Почтовое отделение не найдено с ID: {}", postOfficeId);
                        return new IllegalArgumentException("Почтовое отделение не найдено");
                    });
        }
        mailMovement.setPostOffice(postOffice);

        // Проверка существования MailItem
        MailItem mailItem = mailMovement.getMailItem();
        if (mailItem == null || mailItem.getId() == null) {
            log.error("MailItem должен быть указан");
            throw new IllegalArgumentException("MailItem должен быть указан");
        } else {
            log.info("Проверяем существующее MailItem с ID: {}", mailItem.getId());
            Long mailItemId = mailItem.getId();
            mailItem = mailItemRepository.findById(mailItemId)
                    .orElseThrow(() -> {
                        log.error("MailItem не найдено с ID: {}", mailItemId);
                        return new IllegalArgumentException("MailItem не найдено");
                    });
        }
        mailMovement.setMailItem(mailItem);

        // Установка даты движения
        mailMovement.setMovementDate(new Date());
        log.info("Установлена дата движения для почтового движения: {}", mailMovement.getMovementDate());

        // Сохранение MailMovement
        MailMovement savedMovement = mailMovementRepository.save(mailMovement);
        log.info("Почтовое движение успешно сохранено с ID: {}", savedMovement.getId());

        return savedMovement;
    }
}
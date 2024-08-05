package com.example.tracking.service.impl;

import com.example.tracking.dto.postOfficeDTO.PostOfficeRequestDTO;
import com.example.tracking.dto.postOfficeDTO.PostOfficeResponseDTO;
import com.example.tracking.exception.PostOfficeNotFoundException;
import com.example.tracking.model.PostOffice;
import com.example.tracking.repository.PostOfficeRepository;
import com.example.tracking.service.PostOfficeService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class PostOfficeServiceImpl implements PostOfficeService {

    @Autowired
    private PostOfficeRepository postOfficeRepository;

    @Transactional
    public PostOfficeResponseDTO createPostOffice(PostOfficeRequestDTO postOfficeRequestDTO) {
        log.info("Создание почтового отделения с индексом: {}", postOfficeRequestDTO.getIndex());

        // Проверка входных данных
        if (postOfficeRequestDTO.getIndex() == null || postOfficeRequestDTO.getIndex().isEmpty()) {
            throw new IllegalArgumentException("Индекс почтового отделения не может быть пустым");
        }
        if (postOfficeRequestDTO.getName() == null || postOfficeRequestDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Имя почтового отделения не может быть пустым");
        }
        if (postOfficeRequestDTO.getAddress() == null || postOfficeRequestDTO.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Адрес почтового отделения не может быть пустым");
        }

        PostOffice postOffice = new PostOffice();
        postOffice.setIndex(postOfficeRequestDTO.getIndex());
        postOffice.setName(postOfficeRequestDTO.getName());
        postOffice.setAddress(postOfficeRequestDTO.getAddress());

        postOffice = postOfficeRepository.save(postOffice);
        log.info("Почтовое отделение успешно создано с ID: {}", postOffice.getId());

        return new PostOfficeResponseDTO(
                postOffice.getId(),
                postOffice.getIndex(),
                postOffice.getName(),
                postOffice.getAddress()
        );
    }

    public PostOfficeResponseDTO getPostOffice(String index) {
        log.info("Получение почтового отделения с индексом: {}", index);

        PostOffice postOffice = postOfficeRepository.findByIndex(index)
                .orElseThrow(() -> new PostOfficeNotFoundException("Почтовое отделение не найдено с индексом: " + index));

        log.info("Почтовое отделение найдено: {}", postOffice.getName());

        return new PostOfficeResponseDTO(
                postOffice.getId(),
                postOffice.getIndex(),
                postOffice.getName(),
                postOffice.getAddress()
        );
    }
}
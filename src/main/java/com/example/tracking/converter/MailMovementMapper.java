package com.example.tracking.converter;

import com.example.tracking.dto.MailMovementDTO;
import com.example.tracking.model.MailMovement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MailMovementMapper {
    MailMovementMapper INSTANCE = Mappers.getMapper(MailMovementMapper.class);

    MailMovementDTO toDTO(MailMovement mailMovement);
    MailMovement toEntity(MailMovementDTO mailMovementDTO);
}
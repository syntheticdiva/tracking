package com.example.tracking.controller;

import com.example.tracking.dto.postOfficeDTO.PostOfficeRequestDTO;
import com.example.tracking.dto.postOfficeDTO.PostOfficeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface PostOfficeControllerApi {

    @Operation(summary = "Создать новое почтовое отделение", description = "Создает новое почтовое отделение и возвращает его детали.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Почтовое отделение успешно создано",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostOfficeResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content)
    })
    ResponseEntity<PostOfficeResponseDTO> createPostOffice(
            @Valid @Parameter(description = "Данные для создания почтового отделения", required = true) PostOfficeRequestDTO postOfficeRequestDTO);

    @Operation(summary = "Получить почтовое отделение по индексу", description = "Извлекает информацию о почтовом отделении по его индексу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Почтовое отделение успешно найдено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostOfficeResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отделение не найдено",
                    content = @Content)
    })
    ResponseEntity<PostOfficeResponseDTO> getPostOffice(
            @Parameter(description = "Индекс почтового отделения", required = true) String index);
}
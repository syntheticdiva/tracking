package com.example.tracking.controller;

import com.example.tracking.dto.mailItemDTO.MailItemDTO;
import com.example.tracking.dto.MailMovementDTO;
import com.example.tracking.dto.mailItemDTO.MailItemRequestDTO;
import com.example.tracking.dto.mailItemDTO.MailItemResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;
public interface MailItemControllerApi {
    @Operation(summary = "Зарегистрировать новое почтовое отправление", description = "Создает новое почтовое отправление и возвращает его детали.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Почтовое отправление успешно создано",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailItemResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content)
    })
    ResponseEntity<MailItemResponseDTO> registerMailItem(
            @Valid @Parameter(description = "Данные для регистрации почтового отправления", required = true) MailItemRequestDTO mailItemRequestDTO);

    @Operation(summary = "Добавить прибытие почтового отправления", description = "Записывает прибытие почтового отправления в почтовое отделение.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Прибытие успешно добавлено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailMovementDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление или почтовое отделение не найдены",
                    content = @Content)
    })
    ResponseEntity<MailMovementDTO> addArrival(
            @Parameter(description = "ID почтового отправления", required = true) Long id,
            @Parameter(description = "ID почтового отделения", required = true) Long postOfficeId);

    @Operation(summary = "Добавить отправление почтового отправления", description = "Записывает отправление почтового отправления из почтового отделения.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Отправление успешно добавлено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailMovementDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление или почтовое отделение не найдены",
                    content = @Content)
    })
    ResponseEntity<MailMovementDTO> addDeparture(
            @Parameter(description = "ID почтового отправления", required = true) Long id,
            @Parameter(description = "ID почтового отделения", required = true) Long postOfficeId);

    @Operation(summary = "Отметить почтовое отправление как доставленное", description = "Записывает доставку почтового отправления.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Почтовое отправление отмечено как доставленное",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailMovementDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление или почтовое отделение не найдены",
                    content = @Content)
    })
    ResponseEntity<MailMovementDTO> markAsDelivered(
            @Parameter(description = "ID почтового отправления", required = true) Long id,
            @Parameter(description = "ID почтового отделения", required = true) Long postOfficeId);

    @Operation(summary = "Получить историю почтового отправления", description = "Извлекает историю перемещений для почтового отправления.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История почтового отправления успешно получена",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MailMovementDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление не найдено",
                    content = @Content)
    })
    ResponseEntity<List<MailMovementDTO>> getMailHistory(
            @Parameter(description = "ID почтового отправления", required = true) Long id);

    @Operation(summary = "Получить статус почтового отправления", description = "Извлекает текущий статус почтового отправления.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус почтового отправления успешно получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailItemDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление не найдено",
                    content = @Content)
    })
    ResponseEntity<MailItemDTO> getMailItemStatus(
            @Parameter(description = "ID почтового отправления", required = true) Long id);
}
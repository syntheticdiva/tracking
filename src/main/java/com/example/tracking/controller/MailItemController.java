package com.example.tracking.controller;

import com.example.tracking.dto.mailItemDTO.MailItemDTO;
import com.example.tracking.dto.MailMovementDTO;
import com.example.tracking.dto.mailItemDTO.MailItemRequestDTO;
import com.example.tracking.dto.mailItemDTO.MailItemResponseDTO;
import com.example.tracking.service.impl.MailItemServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/mail-items")
@Tag(name = "Почтовые отправления", description = "Управление почтовыми отправлениями")
@Validated // Добавлено для активации валидации на уровне контроллера
public class MailItemController implements MailItemControllerApi {

    @Autowired
    private MailItemServiceImpl mailItemService;

    @Override
    @PostMapping
    @Operation(summary = "Зарегистрировать новое почтовое отправление", description = "Создает новое почтовое отправление и возвращает его детали.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Почтовое отправление успешно создано",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailItemResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content)
    })
    public ResponseEntity<MailItemResponseDTO> registerMailItem(@Valid @RequestBody MailItemRequestDTO mailItemRequestDTO) {
        MailItemResponseDTO createdMailItem = mailItemService.registerMailItem(mailItemRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMailItem);
    }

    @Override
    @PostMapping("/{id}/movements/arrival")
    @Operation(summary = "Добавить прибытие почтового отправления", description = "Записывает прибытие почтового отправления в почтовое отделение.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Прибытие успешно добавлено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailMovementDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление или почтовое отделение не найдены",
                    content = @Content)
    })
    public ResponseEntity<MailMovementDTO> addArrival(
            @Parameter(description = "ID почтового отправления", required = true) @PathVariable Long id,
            @Parameter(description = "ID почтового отделения", required = true) @RequestParam Long postOfficeId) {
        MailMovementDTO movementDTO = mailItemService.addArrival(id, postOfficeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(movementDTO);
    }

    @Override
    @PostMapping("/{id}/movements/departure")
    @Operation(summary = "Добавить отправление почтового отправления", description = "Записывает отправление почтового отправления из почтового отделения.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Отправление успешно добавлено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailMovementDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление или почтовое отделение не найдены",
                    content = @Content)
    })
    public ResponseEntity<MailMovementDTO> addDeparture(
            @Parameter(description = "ID почтового отправления", required = true) @PathVariable Long id,
            @Parameter(description = "ID почтового отделения", required = true) @RequestParam Long postOfficeId) {
        MailMovementDTO movementDTO = mailItemService.addDeparture(id, postOfficeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(movementDTO);
    }

    @Override
    @PostMapping("/{id}/movements/delivery")
    @Operation(summary = "Отметить почтовое отправление как доставленное", description = "Записывает доставку почтового отправления.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Почтовое отправление отмечено как доставленное",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailMovementDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление или почтовое отделение не найдены",
                    content = @Content)
    })
    public ResponseEntity<MailMovementDTO> markAsDelivered(
            @Parameter(description = "ID почтового отправления", required = true) @PathVariable Long id,
            @Parameter(description = "ID почтового отделения", required = true) @RequestParam Long postOfficeId) {
        MailMovementDTO movementDTO = mailItemService.markAsDelivered(id, postOfficeId);
        return ResponseEntity.ok(movementDTO);
    }

    @Override
    @GetMapping("/{id}/history")
    @Operation(summary = "Получить историю почтового отправления", description = "Извлекает историю перемещений для почтового отправления.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "История почтового отправления успешно получена",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MailMovementDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление не найдено",
                    content = @Content)
    })
    public ResponseEntity<List<MailMovementDTO>> getMailHistory(
            @Parameter(description = "ID почтового отправления", required = true) @PathVariable Long id) {
        List<MailMovementDTO> movements = mailItemService.getMailHistory(id);
        return ResponseEntity.ok(movements);
    }

    @Override
    @GetMapping("/{id}/status")
    @Operation(summary = "Получить статус почтового отправления", description = "Извлекает текущий статус почтового отправления.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус почтового отправления успешно получен",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MailItemDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отправление не найдено",
                    content = @Content)
    })
    public ResponseEntity<MailItemDTO> getMailItemStatus(
            @Parameter(description = "ID почтового отправления", required = true) @PathVariable Long id) {
        MailItemDTO mailItemDTO = mailItemService.getMailItemStatus(id);
        return ResponseEntity.ok(mailItemDTO);
    }
}
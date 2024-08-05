package com.example.tracking.controller;

import com.example.tracking.dto.postOfficeDTO.PostOfficeRequestDTO;
import com.example.tracking.dto.postOfficeDTO.PostOfficeResponseDTO;
import com.example.tracking.exception.PostOfficeNotFoundException;
import com.example.tracking.service.impl.PostOfficeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/post-offices")
@Tag(name = "Почтовые отделения", description = "Управление почтовыми отделениями")
@Validated // Добавлено для активации валидации на уровне контроллера
public class PostOfficeController implements PostOfficeControllerApi {

    @Autowired
    private PostOfficeServiceImpl postOfficeService;

    @Override
    @PostMapping
    @Operation(summary = "Создать новое почтовое отделение", description = "Создает новое почтовое отделение и возвращает его детали.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Почтовое отделение успешно создано",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostOfficeResponseDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные входные данные",
                    content = @Content)
    })
    public ResponseEntity<PostOfficeResponseDTO> createPostOffice(@Valid @RequestBody PostOfficeRequestDTO postOfficeRequestDTO) {
        PostOfficeResponseDTO createdPostOffice = postOfficeService.createPostOffice(postOfficeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPostOffice);
    }

    @Override
    @GetMapping("/{index}")
    @Operation(summary = "Получить почтовое отделение по индексу", description = "Извлекает информацию о почтовом отделении по его индексу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Почтовое отделение успешно найдено",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostOfficeResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Почтовое отделение не найдено",
                    content = @Content)
    })
    public ResponseEntity<PostOfficeResponseDTO> getPostOffice(
            @Parameter(description = "Индекс почтового отделения", required = true) @PathVariable String index) {
        try {
            PostOfficeResponseDTO postOfficeDTO = postOfficeService.getPostOffice(index);
            return ResponseEntity.ok(postOfficeDTO);
        } catch (PostOfficeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        }
    }
}
package com.example.tracking.controller;

import com.example.tracking.dto.MailMovementDTO;
import com.example.tracking.dto.mailItemDTO.MailItemDTO;
import com.example.tracking.dto.mailItemDTO.MailItemRequestDTO;
import com.example.tracking.dto.mailItemDTO.MailItemResponseDTO;
import com.example.tracking.enums.MailItemStatus;
import com.example.tracking.enums.MailItemType;
import com.example.tracking.enums.MailMovementAction;
import com.example.tracking.service.impl.MailItemServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MailItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MailItemServiceImpl mailItemServiceImpl;

    @InjectMocks
    private MailItemController mailItemController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mailItemController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void registerMailItem_ShouldReturnCreatedMailItem() throws Exception {
        MailItemRequestDTO requestDTO = new MailItemRequestDTO("John Doe", MailItemType.LETTER, "123456", "123 Main St");
        MailItemResponseDTO responseDTO = new MailItemResponseDTO(1L, "John Doe", MailItemType.LETTER, "123456", "123 Main St", MailItemStatus.REGISTERED.name(), new Date());

        when(mailItemServiceImpl.registerMailItem(any(MailItemRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/mail-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.recipientName").value("John Doe"))
                .andExpect(jsonPath("$.status").value(MailItemStatus.REGISTERED.name()));
    }

    @Test
    public void addArrival_ShouldReturnMailMovement() throws Exception {
        MailMovementDTO movementDTO = new MailMovementDTO(1L, 1L, 1L, new Date(), MailMovementAction.ARRIVAL.name());

        when(mailItemServiceImpl.addArrival(anyLong(), anyLong())).thenReturn(movementDTO);

        mockMvc.perform(post("/mail-items/1/movements/arrival?postOfficeId=1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.action").value(MailMovementAction.ARRIVAL.name()));
    }

    @Test
    public void addDeparture_ShouldReturnMailMovement() throws Exception {
        MailMovementDTO movementDTO = new MailMovementDTO(1L, 1L, 1L, new Date(), MailMovementAction.DEPARTURE.name());

        when(mailItemServiceImpl.addDeparture(anyLong(), anyLong())).thenReturn(movementDTO);

        mockMvc.perform(post("/mail-items/1/movements/departure?postOfficeId=1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.action").value(MailMovementAction.DEPARTURE.name()));
    }

    @Test
    public void markAsDelivered_ShouldReturnMailMovement() throws Exception {
        MailMovementDTO movementDTO = new MailMovementDTO(1L, 1L, 1L, new Date(), MailMovementAction.DELIVERY.name());

        when(mailItemServiceImpl.markAsDelivered(anyLong(), anyLong())).thenReturn(movementDTO);

        mockMvc.perform(post("/mail-items/1/movements/delivery?postOfficeId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.action").value(MailMovementAction.DELIVERY.name()));
    }

    @Test
    public void getMailHistory_ShouldReturnListOfMovements() throws Exception {
        MailMovementDTO movementDTO = new MailMovementDTO(1L, 1L, 1L, new Date(), MailMovementAction.ARRIVAL.name());
        when(mailItemServiceImpl.getMailHistory(anyLong())).thenReturn(Collections.singletonList(movementDTO));

        mockMvc.perform(get("/mail-items/1/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].action").value(MailMovementAction.ARRIVAL.name()));
    }

    @Test
    public void getMailItemStatus_ShouldReturnMailItem() throws Exception {
        MailItemDTO mailItemDTO = new MailItemDTO(1L, "John Doe", MailItemType.LETTER, "123456", "123 Main St", MailItemStatus.REGISTERED.name(), new Date());
        when(mailItemServiceImpl.getMailItemStatus(anyLong())).thenReturn(mailItemDTO);

        mockMvc.perform(get("/mail-items/1/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipientName").value("John Doe"))
                .andExpect(jsonPath("$.status").value(MailItemStatus.REGISTERED.name()));
    }
}
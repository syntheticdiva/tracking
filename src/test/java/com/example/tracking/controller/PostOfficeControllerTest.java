package com.example.tracking.controller;

import com.example.tracking.dto.postOfficeDTO.PostOfficeRequestDTO;
import com.example.tracking.dto.postOfficeDTO.PostOfficeResponseDTO;
import com.example.tracking.service.impl.PostOfficeServiceImpl;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PostOfficeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PostOfficeServiceImpl postOfficeServiceImpl;

    @InjectMocks
    private PostOfficeController postOfficeController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postOfficeController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void createPostOffice_ShouldReturnCreatedPostOffice() throws Exception {
        PostOfficeRequestDTO requestDTO = new PostOfficeRequestDTO("123456", "Main Post Office", "123 Main St");
        PostOfficeResponseDTO responseDTO = new PostOfficeResponseDTO(1L, "123456", "Main Post Office", "123 Main St");

        when(postOfficeServiceImpl.createPostOffice(any(PostOfficeRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/post-offices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Main Post Office"));
    }

    @Test
    public void getPostOffice_ShouldReturnPostOffice() throws Exception {
        PostOfficeResponseDTO responseDTO = new PostOfficeResponseDTO(1L, "123456", "Main Post Office", "123 Main St");

        when(postOfficeServiceImpl.getPostOffice(anyString())).thenReturn(responseDTO);

        mockMvc.perform(get("/post-offices/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Main Post Office"));
    }
}
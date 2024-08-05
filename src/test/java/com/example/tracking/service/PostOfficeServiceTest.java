package com.example.tracking.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.tracking.dto.postOfficeDTO.PostOfficeRequestDTO;
import com.example.tracking.dto.postOfficeDTO.PostOfficeResponseDTO;
import com.example.tracking.exception.PostOfficeNotFoundException;
import com.example.tracking.model.PostOffice;
import com.example.tracking.repository.PostOfficeRepository;
import com.example.tracking.service.impl.PostOfficeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PostOfficeServiceTest {

    @Mock
    private PostOfficeRepository postOfficeRepository;

    @InjectMocks
    private PostOfficeServiceImpl postOfficeServiceImpl;

    @Test
    public void testCreatePostOffice_Success() {
        PostOfficeRequestDTO requestDTO = new PostOfficeRequestDTO();
        requestDTO.setIndex("123456");
        requestDTO.setName("Test Post Office");
        requestDTO.setAddress("123 Main St");

        PostOffice savedPostOffice = new PostOffice();
        savedPostOffice.setId(1L);
        savedPostOffice.setIndex(requestDTO.getIndex());
        savedPostOffice.setName(requestDTO.getName());
        savedPostOffice.setAddress(requestDTO.getAddress());

        when(postOfficeRepository.save(any(PostOffice.class))).thenReturn(savedPostOffice);

        PostOfficeResponseDTO responseDTO = postOfficeServiceImpl.createPostOffice(requestDTO);

        assertEquals(savedPostOffice.getId(), responseDTO.getId());
        assertEquals(savedPostOffice.getIndex(), responseDTO.getIndex());
        assertEquals(savedPostOffice.getName(), responseDTO.getName());
        assertEquals(savedPostOffice.getAddress(), responseDTO.getAddress());
    }

    @Test
    public void testGetPostOffice_Success() {
        String index = "123456";

        PostOffice postOffice = new PostOffice();
        postOffice.setId(1L);
        postOffice.setIndex(index);
        postOffice.setName("Test Post Office");
        postOffice.setAddress("123 Main St");

        when(postOfficeRepository.findByIndex(index)).thenReturn(Optional.of(postOffice));

        PostOfficeResponseDTO responseDTO = postOfficeServiceImpl.getPostOffice(index);

        assertEquals(postOffice.getId(), responseDTO.getId());
        assertEquals(postOffice.getIndex(), responseDTO.getIndex());
        assertEquals(postOffice.getName(), responseDTO.getName());
        assertEquals(postOffice.getAddress(), responseDTO.getAddress());
    }

    @Test
    public void testGetPostOffice_NotFound() {
        String index = "123456";

        when(postOfficeRepository.findByIndex(index)).thenReturn(Optional.empty());

        assertThrows(PostOfficeNotFoundException.class, () -> {
            postOfficeServiceImpl.getPostOffice(index);
        });
    }
}

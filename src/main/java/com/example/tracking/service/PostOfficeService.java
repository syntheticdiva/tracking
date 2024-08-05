package com.example.tracking.service;

import com.example.tracking.dto.postOfficeDTO.PostOfficeRequestDTO;
import com.example.tracking.dto.postOfficeDTO.PostOfficeResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface PostOfficeService {
    PostOfficeResponseDTO createPostOffice(PostOfficeRequestDTO postOfficeRequestDTO);
    PostOfficeResponseDTO getPostOffice(String index);
}


package com.example.tracking.converter;

import com.example.tracking.dto.postOfficeDTO.PostOfficeDTO;
import com.example.tracking.model.PostOffice;
import org.springframework.stereotype.Component;

@Component
public class PostOfficeConverter {
    public static PostOfficeDTO toDTO(PostOffice postOffice) {
        if (postOffice == null) {
            return null;
        }
        return new PostOfficeDTO(
                postOffice.getIndex(),
                postOffice.getName(),
                postOffice.getAddress()
        );
    }

    public static PostOffice toEntity(PostOfficeDTO postOfficeDTO) {
        if (postOfficeDTO == null) {
            return null;
        }
        PostOffice postOffice = new PostOffice();
        postOffice.setIndex(postOfficeDTO.getIndex());
        postOffice.setName(postOfficeDTO.getName());
        postOffice.setAddress(postOfficeDTO.getAddress());
        return postOffice;
    }
}
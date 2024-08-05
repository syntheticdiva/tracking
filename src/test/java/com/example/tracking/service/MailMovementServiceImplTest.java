package com.example.tracking.service;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.tracking.model.MailItem;
import com.example.tracking.model.MailMovement;
import com.example.tracking.model.PostOffice;
import com.example.tracking.repository.MailItemRepository;
import com.example.tracking.repository.MailMovementRepository;
import com.example.tracking.repository.PostOfficeRepository;
import com.example.tracking.service.impl.MailMovementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MailMovementServiceImplTest {

    @Mock
    private MailMovementRepository mailMovementRepository;

    @Mock
    private PostOfficeRepository postOfficeRepository;

    @Mock
    private MailItemRepository mailItemRepository;

    @InjectMocks
    private MailMovementServiceImpl mailMovementServiceImpl;

    private MailMovement mailMovement;
    private PostOffice postOffice;
    private MailItem mailItem;

    @BeforeEach
    public void setUp() {
        postOffice = new PostOffice();
        postOffice.setId(1L); // Установите ID для теста

        mailItem = new MailItem();
        mailItem.setId(1L); // Установите ID для теста

        mailMovement = new MailMovement();
        mailMovement.setPostOffice(postOffice);
        mailMovement.setMailItem(mailItem);
    }

    @Test
    public void testSaveMailMovement_Success() {
        when(postOfficeRepository.findById(String.valueOf(postOffice.getId()))).thenReturn(Optional.of(postOffice));
        when(mailItemRepository.findById(mailItem.getId())).thenReturn(Optional.of(mailItem));
        when(mailMovementRepository.save(any(MailMovement.class))).thenReturn(mailMovement);

        MailMovement savedMovement = mailMovementServiceImpl.saveMailMovement(mailMovement);

        verify(postOfficeRepository).findById(String.valueOf(postOffice.getId()));
        verify(mailItemRepository).findById(mailItem.getId());
        verify(mailMovementRepository).save(mailMovement);
        assertNotNull(savedMovement);
        assertEquals(mailMovement, savedMovement);
    }

    @Test
    public void testSaveMailMovement_NonExistentPostOffice() {
        mailMovement.setPostOffice(new PostOffice()); // Новое почтовое отделение без ID

        when(postOfficeRepository.save(any(PostOffice.class))).thenReturn(postOffice);
        when(mailItemRepository.findById(mailItem.getId())).thenReturn(Optional.of(mailItem));
        when(mailMovementRepository.save(any(MailMovement.class))).thenReturn(mailMovement);

        MailMovement savedMovement = mailMovementServiceImpl.saveMailMovement(mailMovement);

        verify(postOfficeRepository).save(any(PostOffice.class));
        verify(mailItemRepository).findById(mailItem.getId());
        verify(mailMovementRepository).save(mailMovement);
        assertNotNull(savedMovement);
        assertEquals(mailMovement, savedMovement);
    }


    @Test
    public void testSaveMailMovement_MailItemNotFound() {
        when(postOfficeRepository.findById(String.valueOf(postOffice.getId()))).thenReturn(Optional.of(postOffice));
        when(mailItemRepository.findById(mailItem.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mailMovementServiceImpl.saveMailMovement(mailMovement);
        });

        assertEquals("MailItem не найдено", exception.getMessage());
        verify(mailMovementRepository, never()).save(any(MailMovement.class));
    }

    @Test
    public void testSaveMailMovement_PostOfficeNotFound() {
        when(postOfficeRepository.findById(String.valueOf(postOffice.getId()))).thenReturn(Optional.empty());
        when(mailItemRepository.findById(mailItem.getId())).thenReturn(Optional.of(mailItem));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            mailMovementServiceImpl.saveMailMovement(mailMovement);
        });

        assertEquals("Почтовое отделение не найдено", exception.getMessage());
        verify(mailMovementRepository, never()).save(any(MailMovement.class));
    }
}
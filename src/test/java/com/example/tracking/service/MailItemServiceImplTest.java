package com.example.tracking.service;
import com.example.tracking.converter.MailMovementConverter;
import com.example.tracking.dto.MailMovementDTO;
import com.example.tracking.dto.mailItemDTO.MailItemDTO;
import com.example.tracking.dto.mailItemDTO.MailItemRequestDTO;
import com.example.tracking.dto.mailItemDTO.MailItemResponseDTO;
import com.example.tracking.enums.MailItemStatus;
import com.example.tracking.enums.MailItemType;
import com.example.tracking.enums.MailMovementAction;
import com.example.tracking.converter.MailMovementConverter;
import com.example.tracking.model.MailItem;
import com.example.tracking.model.MailMovement;
import com.example.tracking.model.PostOffice;
import com.example.tracking.repository.MailItemRepository;
import com.example.tracking.repository.MailMovementRepository;
import com.example.tracking.repository.PostOfficeRepository;
import com.example.tracking.service.impl.MailItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailItemServiceImplTest {

    @Mock
    private MailItemRepository mailItemRepository;

    @Mock
    private MailMovementRepository mailMovementRepository;

    @Mock
    private PostOfficeRepository postOfficeRepository;

    @Mock
    private MailMovementConverter mailMovementConverter;
    @InjectMocks
    private MailItemServiceImpl mailItemServiceImpl;

    @BeforeEach
    void setUp() {
        // Инициализируйте любые необходимые заглушки или данные тестов
    }

    @Test
    void testRegisterMailItem() {
        // Arrange
        MailItemRequestDTO requestDTO = new MailItemRequestDTO("John Doe", MailItemType.LETTER, "123456", "123 Main St");
        MailItem savedMailItem = new MailItem();
        savedMailItem.setId(1L);
        savedMailItem.setRecipientName(requestDTO.getRecipientName());
        savedMailItem.setRecipientAddress(requestDTO.getRecipientAddress());
        savedMailItem.setRecipientIndex(requestDTO.getRecipientIndex());
        savedMailItem.setType(requestDTO.getType());
        savedMailItem.setStatus(MailItemStatus.REGISTERED);
        savedMailItem.setCreatedAt(new Date());

        MailItemDTO expectedDTO = new MailItemDTO(
                savedMailItem.getId(),
                savedMailItem.getRecipientName(),
                savedMailItem.getType(),
                savedMailItem.getRecipientIndex(),
                savedMailItem.getRecipientAddress(),
                savedMailItem.getStatus().name(),
                savedMailItem.getCreatedAt()
        );

        when(mailItemRepository.save(any(MailItem.class))).thenReturn(savedMailItem);

        // Act
        MailItemResponseDTO responseDTO = mailItemServiceImpl.registerMailItem(requestDTO);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(expectedDTO.getId(), responseDTO.getId());
        assertEquals(expectedDTO.getRecipientName(), responseDTO.getRecipientName());
        assertEquals(expectedDTO.getType(), responseDTO.getType());
        assertEquals(expectedDTO.getRecipientIndex(), responseDTO.getRecipientIndex());
        assertEquals(expectedDTO.getRecipientAddress(), responseDTO.getRecipientAddress());
        assertEquals(expectedDTO.getStatus(), responseDTO.getStatus());
        assertEquals(expectedDTO.getCreatedAt(), responseDTO.getCreatedAt());
    }

    @Test
    void testAddArrival() {
        // Arrange
        Long mailItemId = 1L;
        Long postOfficeId = 1L;

        MailItem mailItem = new MailItem();
        mailItem.setId(mailItemId);
        mailItem.setStatus(MailItemStatus.REGISTERED);

        PostOffice postOffice = new PostOffice();
        postOffice.setId(Long.valueOf(String.valueOf(postOfficeId)));

        MailMovement savedMovement = new MailMovement();
        savedMovement.setId(1L);
        savedMovement.setMailItem(mailItem);
        savedMovement.setPostOffice(postOffice);
        savedMovement.setMovementDate(new Date());
        savedMovement.setAction(MailMovementAction.ARRIVAL);
        savedMovement.setStatus(MailItemStatus.ACCEPTED);
        MailMovementDTO expectedDTO = new MailMovementDTO();
        expectedDTO.setId(savedMovement.getId());

        when(mailItemRepository.findById(mailItemId)).thenReturn(Optional.of(mailItem));
        when(postOfficeRepository.findById(String.valueOf(postOfficeId))).thenReturn(Optional.of(postOffice));
        when(mailMovementRepository.save(any(MailMovement.class))).thenReturn(savedMovement);
        when(mailMovementConverter.toDTO(savedMovement)).thenReturn(expectedDTO);

        // Act
        MailMovementDTO actualDTO = mailItemServiceImpl.addArrival(mailItemId, postOfficeId);

        // Assert
        assertNotNull(actualDTO);
        assertEquals(expectedDTO.getId(), actualDTO.getId());
    }

    @Test
    void testAddDeparture() {
        // Arrange
        Long mailItemId = 1L;
        Long postOfficeId = 1L;

        MailItem mailItem = new MailItem();
        mailItem.setId(mailItemId);
        mailItem.setStatus(MailItemStatus.ACCEPTED);

        PostOffice postOffice = new PostOffice();
        postOffice.setId(Long.valueOf(String.valueOf(postOfficeId)));

        MailMovement savedMovement = new MailMovement();
        savedMovement.setId(1L);
        savedMovement.setMailItem(mailItem);
        savedMovement.setPostOffice(postOffice);
        savedMovement.setMovementDate(new Date());
        savedMovement.setAction(MailMovementAction.DEPARTURE);
        savedMovement.setStatus(MailItemStatus.IN_TRANSIT);

        MailMovementDTO expectedDTO = new MailMovementDTO();
        expectedDTO.setId(savedMovement.getId());

        when(mailItemRepository.findById(mailItemId)).thenReturn(Optional.of(mailItem));
        when(postOfficeRepository.findById(String.valueOf(postOfficeId))).thenReturn(Optional.of(postOffice));
        when(mailMovementRepository.save(any(MailMovement.class))).thenReturn(savedMovement);
        when(mailMovementConverter.toDTO(savedMovement)).thenReturn(expectedDTO);

        // Act
        MailMovementDTO actualDTO = mailItemServiceImpl.addDeparture(mailItemId, postOfficeId);

        // Assert
        assertNotNull(actualDTO);
        assertEquals(expectedDTO.getId(), actualDTO.getId());
    }

    @Test
    void testMarkAsDelivered() {
        // Arrange
        Long mailItemId = 1L;
        Long postOfficeId = 1L;

        MailItem mailItem = new MailItem();
        mailItem.setId(mailItemId);
        mailItem.setStatus(MailItemStatus.IN_TRANSIT);

        PostOffice postOffice = new PostOffice();
        postOffice.setId(Long.valueOf(String.valueOf(postOfficeId)));

        MailMovement savedMovement = new MailMovement();
        savedMovement.setId(1L);
        savedMovement.setMailItem(mailItem);
        savedMovement.setPostOffice(postOffice);
        savedMovement.setMovementDate(new Date());
        savedMovement.setAction(MailMovementAction.DELIVERY);
        savedMovement.setStatus(MailItemStatus.DELIVERED);

        MailMovementDTO expectedDTO = new MailMovementDTO();
        expectedDTO.setId(savedMovement.getId());

        when(mailItemRepository.findById(mailItemId)).thenReturn(Optional.of(mailItem));
        when(postOfficeRepository.findById(String.valueOf(postOfficeId))).thenReturn(Optional.of(postOffice));
        when(mailMovementRepository.save(any(MailMovement.class))).thenReturn(savedMovement);
        when(mailMovementConverter.toDTO(savedMovement)).thenReturn(expectedDTO);

        // Act
        MailMovementDTO actualDTO = mailItemServiceImpl.markAsDelivered(mailItemId, postOfficeId);

        // Assert
        assertNotNull(actualDTO);
        assertEquals(expectedDTO.getId(), actualDTO.getId());
    }

    @Test
    void testUpdateMailItemStatus() {
        // Arrange
        Long mailItemId = 1L;
        MailItemStatus newStatus = MailItemStatus.IN_TRANSIT;

        MailItem mailItem = new MailItem();
        mailItem.setId(mailItemId);
        mailItem.setStatus(MailItemStatus.DELIVERED);

        when(mailItemRepository.findById(mailItemId)).thenReturn(Optional.of(mailItem));
        when(mailItemRepository.save(any(MailItem.class))).thenReturn(mailItem);

        // Act
        mailItemServiceImpl.updateMailItemStatus(mailItemId, newStatus);

        // Assert
        assertEquals(newStatus, mailItem.getStatus());
        verify(mailItemRepository, times(1)).save(mailItem);
    }

    @Test
    void testGetMailItemStatus() {
        // Arrange
        Long mailItemId = 1L;

        MailItem mailItem = new MailItem();
        mailItem.setId(mailItemId);
        mailItem.setRecipientName("John Doe");
        mailItem.setRecipientAddress("123 Main St");
        mailItem.setRecipientIndex("123456");
        mailItem.setType(MailItemType.LETTER);
        mailItem.setStatus(MailItemStatus.DELIVERED);
        mailItem.setCreatedAt(new Date());

        MailItemDTO expectedDTO = new MailItemDTO(
                mailItem.getId(),
                mailItem.getRecipientName(),
                mailItem.getType(),
                mailItem.getRecipientIndex(),
                mailItem.getRecipientAddress(),
                mailItem.getStatus().name(),
                mailItem.getCreatedAt()
        );

        when(mailItemRepository.findById(mailItemId)).thenReturn(Optional.of(mailItem));

        // Act
        MailItemDTO actualDTO = mailItemServiceImpl.getMailItemStatus(mailItemId);

        // Assert
        assertNotNull(actualDTO);
        assertEquals(expectedDTO.getId(), actualDTO.getId());
        assertEquals(expectedDTO.getRecipientName(), actualDTO.getRecipientName());
        assertEquals(expectedDTO.getType(), actualDTO.getType());
        assertEquals(expectedDTO.getRecipientIndex(), actualDTO.getRecipientIndex());
        assertEquals(expectedDTO.getRecipientAddress(), actualDTO.getRecipientAddress());
        assertEquals(expectedDTO.getStatus(), actualDTO.getStatus());
        assertEquals(expectedDTO.getCreatedAt(), actualDTO.getCreatedAt());
    }

    @Test
    void testGetMailHistory() {
        // Arrange
        Long mailItemId = 1L;

        MailItem mailItem = new MailItem();
        mailItem.setId(mailItemId);

        MailMovement movement1 = new MailMovement();
        movement1.setId(1L);
        movement1.setMailItem(mailItem);
        movement1.setAction(MailMovementAction.ARRIVAL);

        MailMovement movement2 = new MailMovement();
        movement2.setId(2L);
        movement2.setMailItem(mailItem);
        movement2.setAction(MailMovementAction.DEPARTURE);

        List<MailMovement> movements = new ArrayList<>();
        movements.add(movement1);
        movements.add(movement2);

        when(mailItemRepository.findById(mailItemId)).thenReturn(Optional.of(mailItem));
        when(mailMovementRepository.findByMailItem(mailItem)).thenReturn(movements);
        when(mailMovementConverter.toDTO(movement1)).thenReturn(new MailMovementDTO());
        when(mailMovementConverter.toDTO(movement2)).thenReturn(new MailMovementDTO());

        // Act
        List<MailMovementDTO> historyDTOs = mailItemServiceImpl.getMailHistory(mailItemId);

        // Assert
        assertNotNull(historyDTOs);
        assertEquals(2, historyDTOs.size());
    }
}


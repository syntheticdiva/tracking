package com.example.tracking.model;

import com.example.tracking.enums.MailItemStatus;
import com.example.tracking.enums.MailItemType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipientName;

    @Enumerated(EnumType.STRING) // Используем строковое представление для хранения в БД
    private MailItemType type; // Письмо, посылка, бандероль, открытка

    private String recipientIndex;
    private String recipientAddress;

    @Enumerated(EnumType.STRING) // Используем строковое представление для хранения в БД
    private MailItemStatus status; // Статус отправления

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "mailItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MailMovement> movements; // История передвижений
}



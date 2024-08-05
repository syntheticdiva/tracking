package com.example.tracking.model;

import com.example.tracking.enums.MailItemStatus;
import com.example.tracking.enums.MailMovementAction;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MailMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Убедитесь, что это настроено правильно
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mail_item_id", nullable = false)
    private MailItem mailItem;

    @ManyToOne
    @JoinColumn(name = "post_office_id", nullable = false) // Измените на id
    private PostOffice postOffice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date movementDate;

    @Enumerated(EnumType.STRING)
    private MailMovementAction action;
    @Enumerated(EnumType.STRING)
    private MailItemStatus status; // Используем MailItemStatus для статуса движения
}
package com.example.tracking.service;

import com.example.tracking.model.MailMovement;
import org.springframework.stereotype.Service;

@Service
public interface MailMovementService {
    MailMovement saveMailMovement(MailMovement mailMovement);
}

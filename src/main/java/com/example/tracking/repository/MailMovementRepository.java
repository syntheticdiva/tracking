package com.example.tracking.repository;

import com.example.tracking.model.MailItem;
import com.example.tracking.model.MailMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MailMovementRepository extends JpaRepository<MailMovement, Long> {
    List<MailMovement> findByMailItem(MailItem mailItem);
}
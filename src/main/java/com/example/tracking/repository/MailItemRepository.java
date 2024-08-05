package com.example.tracking.repository;

import com.example.tracking.model.MailItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailItemRepository extends JpaRepository<MailItem, Long> {
}
package com.example.tracking.repository;

import com.example.tracking.model.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostOfficeRepository extends JpaRepository<PostOffice, String> {
    Optional<PostOffice> findByIndex(String index);
}

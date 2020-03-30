package com.lazar.andric.homework.tender;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenderRepository extends JpaRepository<Tender, Long> {
    List<Tender> findAllByIssuerId(Long issuerId);
}

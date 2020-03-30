package com.lazar.andric.homework.offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findOfferByTenderId(Long tenderId);
}

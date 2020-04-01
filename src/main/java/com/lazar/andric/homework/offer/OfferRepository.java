package com.lazar.andric.homework.offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findOfferByTenderId(Long tenderId);
    List<Offer> findOfferByBidderIdAndTenderId(Long bidderId, Long tenderId);
    List<Offer> findOfferByBidderId(Long bidderId);
    Optional<Offer> findOfferByIdAndTenderId(Long id, Long tenderId);
}

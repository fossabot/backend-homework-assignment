package com.lazar.andric.homework.offer;

import com.lazar.andric.homework.bidder.Bidder;
import com.lazar.andric.homework.bidder.BidderRepository;
import com.lazar.andric.homework.tender.Tender;
import com.lazar.andric.homework.tender.TenderRepository;
import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
import com.lazar.andric.homework.util.exceptions.ExceptionMessageFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final TenderRepository tenderRepository;
    private final BidderRepository bidderRepository;
    private final OfferMapper offerMapper;

    List<OfferDto> getAllOfferForTender(Long tenderId) {
        throwExceptionIfTenderNotExists(tenderId);

        return offerRepository.findOfferByTenderId(tenderId)
                              .stream()
                              .map(offerMapper::toOfferDto)
                              .collect(Collectors.toList());
    }

    List<OfferDto> getAllOfferForBidder(Long bidderId) {
        throwExceptionIfBidderNotExists(bidderId);

        return offerRepository.findOfferByBidderId(bidderId)
                              .stream()
                              .map(offerMapper::toOfferDto)
                              .collect(Collectors.toList());
    }

    List<OfferDto> getAllBiddersOfferForSpecificTender(Long bidderId, Long tenderId) {
        throwExceptionIfBidderNotExists(bidderId);
        throwExceptionIfTenderNotExists(tenderId);

        return offerRepository.findOfferByBidderIdAndTenderId(bidderId, tenderId)
                              .stream()
                              .map(offerMapper::toOfferDto)
                              .collect(Collectors.toList());
    }

    private void throwExceptionIfBidderNotExists(Long bidderId) {
        if( !bidderRepository.existsById(bidderId))
            throw new EntityNotFoundException(ExceptionMessageFormatter.formatEntityNotFoundMessage("Bidder", bidderId));
    }

    private void throwExceptionIfTenderNotExists(Long tenderId) {
        if( !tenderRepository.existsById(tenderId))
            throw new EntityNotFoundException(ExceptionMessageFormatter.formatEntityNotFoundMessage("Tender", tenderId));
    }

    OfferDto saveNewOffer(OfferDto offerDto, Long tenderId, Long bidderId) {
        throwExceptionIfBidderNotExists(bidderId);
        throwExceptionIfTenderNotExists(tenderId);

        Bidder bidder = bidderRepository.getOne(bidderId);
        Tender tender = tenderRepository.getOne(tenderId);
        Offer offer = offerMapper.toOffer(offerDto);
        offer.setBidder(bidder);
        offer.setTender(tender);
        offer.setAccepted(false);
        Offer savedOffer = offerRepository.save(offer);
        return offerMapper.toOfferDto(savedOffer);
    }
}

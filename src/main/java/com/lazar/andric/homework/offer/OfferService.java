package com.lazar.andric.homework.offer;

import com.lazar.andric.homework.tender.TenderRepository;
import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final OfferRepository offerRepository;
    private final TenderRepository tenderRepository;
    private final OfferMapper offerMapper;

    List<OfferDto> getAllOfferForTender(Long tenderId) {
        if (tenderRepository.existsById(tenderId)) {
            return offerRepository.findOfferByTenderId(tenderId)
                                  .stream()
                                  .map(offerMapper::toOfferDto)
                                  .collect(Collectors.toList());
        }
        throw new EntityNotFoundException(String.format("Tender with id %s not found", tenderId));
    }
}

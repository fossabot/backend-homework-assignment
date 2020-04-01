package com.lazar.andric.homework.tender;

import com.lazar.andric.homework.issuer.Issuer;
import com.lazar.andric.homework.issuer.IssuerRepository;
import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
import com.lazar.andric.homework.util.exceptions.ExceptionMessageFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenderService {

    private final IssuerRepository issuerRepository;
    private final TenderRepository tenderRepository;
    private final TenderMapper tenderMapper;

    TenderDto createTenderForIssuer(Long issuerId, TenderDto tenderDto) throws EntityNotFoundException {
        Optional<Issuer> issuer = issuerRepository.findById(issuerId);
        if (issuer.isPresent()) {
            Tender tender = tenderMapper.toTender(tenderDto);
            tender.setIssuer(issuer.get());
            Tender savedTender = tenderRepository.save(tender);
            return tenderMapper.toTenderDto(savedTender);
        }
        throw new EntityNotFoundException(formatExceptionMessage(issuerId));
    }

    List<TenderDto> getAllTendersForIssuers(Long issuerId) throws EntityNotFoundException {
        if (issuerRepository.existsById(issuerId)) {
            return tenderRepository.findAllByIssuerId(issuerId)
                                   .stream()
                                   .map(tenderMapper::toTenderDto)
                                   .collect(Collectors.toList());
        }
        throw new EntityNotFoundException(formatExceptionMessage(issuerId));
    }

    private String formatExceptionMessage(Long issuerId) throws EntityNotFoundException {
        return ExceptionMessageFormatter.formatEntityNotFoundMessage("Issuer", issuerId);
    }

    public void closeTenderForNewOffers(Long tenderId) {
        throwExceptionIfTenderNotExists(tenderId);
        Tender tender = tenderRepository.getOne(tenderId);
        tender.setClosedForOffers(true);
        tenderRepository.save(tender);
    }

    public void throwExceptionIfTenderNotExists(Long tenderId) {
        if( !tenderRepository.existsById(tenderId))
            throw new EntityNotFoundException(ExceptionMessageFormatter.formatEntityNotFoundMessage("Tender", tenderId));
    }
}

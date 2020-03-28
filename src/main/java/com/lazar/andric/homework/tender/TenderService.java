package com.lazar.andric.homework.tender;

import com.lazar.andric.homework.issuer.Issuer;
import com.lazar.andric.homework.issuer.IssuerRepository;
import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenderService {

    private final IssuerRepository issuerRepository;
    private final TenderRepository tenderRepository;
    private final TenderMapper tenderMapper;

    TenderDto createTenderForIssuer(Long issuer_id, TenderDto tenderDto) {
        Optional<Issuer> issuer = issuerRepository.findById(issuer_id);
        if (issuer.isPresent()) {
            Tender tender = tenderMapper.toTender(tenderDto);
            tender.setIssuer(issuer.get());
            Tender savedTender = tenderRepository.save(tender);
            return tenderMapper.toTenderDto(savedTender);
        }
        throw new EntityNotFoundException(String.format("Issuer with id %s not found", issuer_id));
    }
}

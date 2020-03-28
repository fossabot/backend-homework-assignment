package com.lazar.andric.homework.tender;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RequestMapping("issuers")
@RestController
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;

    @PostMapping("{issuer_id}/tenders")
    ResponseEntity<TenderDto> createTenderForIssuer(@NotNull @PathVariable Long issuer_id,
                                                    @NotNull @RequestBody TenderDto tenderDto) {

        return ResponseEntity.ok(tenderService.createTenderForIssuer(issuer_id, tenderDto));
    }
}

package com.lazar.andric.homework.tender;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("issuers")
@RestController
@RequiredArgsConstructor
public class TenderController {

    private final TenderService tenderService;

    @PostMapping("{issuerId}/tenders")
    ResponseEntity<TenderDto> createTenderForIssuer(@NotNull @PathVariable Long issuerId,
                                                    @NotNull @RequestBody TenderDto tenderDto) {

        return ResponseEntity.ok(tenderService.createTenderForIssuer(issuerId, tenderDto));
    }

    @GetMapping("{issuerId}/tenders")
    ResponseEntity<List<TenderDto>> getAllTendersForIssuers(@NotNull @PathVariable Long issuerId) {
        return ResponseEntity.ok(tenderService.getAllTendersForIssuers(issuerId));
    }
}

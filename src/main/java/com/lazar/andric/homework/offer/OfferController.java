package com.lazar.andric.homework.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("tenders/{tenderId}/offers")
    ResponseEntity<List<OfferDto>> getAllOfferForTender(@NotNull @PathVariable Long tenderId) {
        return ResponseEntity.ok(offerService.getAllOfferForTender(tenderId));
    }
}

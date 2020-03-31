package com.lazar.andric.homework.offer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("bidders/{bidderId}/offers")
    ResponseEntity<List<OfferDto>> getOfferForBidder(@NotNull @PathVariable Long bidderId) {
        return ResponseEntity.ok(offerService.getAllOfferForBidder(bidderId));
    }

    @GetMapping("bidders/{bidderId}/tenders/{tenderId}/offers")
    ResponseEntity<List<OfferDto>> getAllBiddersOfferForSpecificTender(@NotNull @PathVariable Long bidderId,
                                                                       @NotNull @PathVariable Long tenderId) {
        return ResponseEntity.ok(offerService.getAllBiddersOfferForSpecificTender(bidderId, tenderId));
    }

    @PostMapping("bidders/{bidderId}/tenders/{tenderId}/offers")
    ResponseEntity<OfferDto> submitNewOffer(@NotNull @PathVariable Long bidderId,
                                            @NotNull @PathVariable Long tenderId,
                                            @RequestBody OfferDto offerDto) {
        return ResponseEntity.ok(offerService.saveNewOffer(offerDto, tenderId, bidderId));
    }
}

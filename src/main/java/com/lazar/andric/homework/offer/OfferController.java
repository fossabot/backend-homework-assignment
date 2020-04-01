package com.lazar.andric.homework.offer;

import com.lazar.andric.homework.tender.TenderService;
import com.lazar.andric.homework.validators.OnCreate;
import com.lazar.andric.homework.validators.TenderOpen;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class OfferController {

    private final OfferService offerService;
    private final TenderService tenderService;

    @GetMapping("tenders/{tenderId}/offers")
    ResponseEntity<List<OfferDto>> getAllOfferForTender(@NotNull @PathVariable Long tenderId) {
        return ResponseEntity.ok(offerService.getAllOfferForTender(tenderId));
    }

    @GetMapping("bidders/{bidderId}/offers")
    ResponseEntity<List<OfferDto>> getOffersForBidder(@NotNull @PathVariable Long bidderId) {
        return ResponseEntity.ok(offerService.getAllOfferForBidder(bidderId));
    }

    @GetMapping("bidders/{bidderId}/tenders/{tenderId}/offers")
    ResponseEntity<List<OfferDto>> getAllBidderOffersForSpecificTender(@NotNull @PathVariable Long bidderId,
                                                                       @NotNull @PathVariable Long tenderId) {
        return ResponseEntity.ok(offerService.getAllBiddersOfferForSpecificTender(bidderId, tenderId));
    }

    @Validated(OnCreate.class)
    @PostMapping("bidders/{bidderId}/tenders/{tenderId}/offers")
    ResponseEntity<OfferDto> submitNewOffer(@NotNull @PathVariable Long bidderId,
                                            @NotNull @TenderOpen(groups = OnCreate.class) @PathVariable Long tenderId,
                                            @Valid @NotNull @RequestBody OfferDto offerDto) {
        return ResponseEntity.ok(offerService.saveNewOffer(offerDto, tenderId, bidderId));
    }

    @PatchMapping("tenders/{tenderId}/offers/{offerId}")
    ResponseEntity<OfferDto> acceptOffer(@NotNull @TenderOpen @PathVariable Long tenderId,
                                  @NotNull @PathVariable Long offerId){
        OfferDto acceptOffer = offerService.acceptOffer(offerId, tenderId);
        tenderService.closeTenderForNewOffers(tenderId);
        return ResponseEntity.ok(acceptOffer);
    }
}

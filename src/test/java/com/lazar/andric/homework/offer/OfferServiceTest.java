package com.lazar.andric.homework.offer;

import com.lazar.andric.homework.tender.TenderRepository;
import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private TenderRepository tenderRepository;

    @Spy
    private OfferMapper offerMapper = Mappers.getMapper(OfferMapper.class);

    private final Long ID = 1L;

    @Test
    void testGetAllOfferForTenderSuccess() {
        List<Offer> offersFromDB = new ArrayList<>();
        offersFromDB.add(Offer.builder().id(1L).amount(200).build());
        offersFromDB.add(Offer.builder().id(2L).amount(300).build());
        List<OfferDto> offersForResponse = offersFromDB.stream().map(offerMapper::toOfferDto).collect(Collectors.toList());

        given(tenderRepository.existsById(ID)).willReturn(true);
        given(offerRepository.findOfferByTenderId(ID)).willReturn(offersFromDB);

        assertThat(offerService.getAllOfferForTender(ID)).isEqualTo(offersForResponse);
    }

    @Test
    void testGetAllOfferForTenderThrowException() {
        given(tenderRepository.existsById(ID)).willReturn(false);

        assertThatThrownBy( () -> {
            offerService.getAllOfferForTender(ID);
        }).isInstanceOf(EntityNotFoundException.class);
    }
}

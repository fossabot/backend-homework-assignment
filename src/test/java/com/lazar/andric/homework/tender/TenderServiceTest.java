package com.lazar.andric.homework.tender;

import com.lazar.andric.homework.issuer.Issuer;
import com.lazar.andric.homework.issuer.IssuerRepository;
import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TenderServiceTest {

    @InjectMocks
    private TenderService tenderService;

    @Mock
    private TenderRepository tenderRepository;

    @Spy
    private final TenderMapper tenderMapper = Mappers.getMapper(TenderMapper.class);

    @Mock
    private IssuerRepository issuerRepository;

    private final TenderDto tenderDtoForSaving = TenderDto.builder().description("description").build();
    private final Long ID = 1L;

    @Test
    void testSaveTender() {
        Issuer issuer = Issuer.builder().id(ID).name("name").build();
        Tender savedTender = Tender.builder()
                                         .id(ID)
                                         .description(tenderDtoForSaving.getDescription())
                                         .issuer(Issuer.builder().id(ID).name(issuer.getName()).build())
                                         .build();

        given(issuerRepository.findById(ID)).willReturn(Optional.of(issuer));
        given(tenderRepository.save(any())).willReturn(savedTender);

        assertThat(tenderService.createTenderForIssuer(ID, tenderDtoForSaving)).isEqualTo(tenderMapper.toTenderDto(savedTender));
    }

    @Test
    void testSaveTenderThrowException() {

        given(issuerRepository.findById(ID)).willReturn(Optional.empty());

        assertThatThrownBy( () -> {
            tenderService.createTenderForIssuer(ID, tenderDtoForSaving);
                }).isInstanceOf(EntityNotFoundException.class);
    }

}

package com.lazar.andric.homework.tender;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TenderMapper {
    TenderDto toTenderDto(Tender tender);
    Tender toTender(TenderDto tenderDto);
}

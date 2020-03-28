package com.lazar.andric.homework.issuer;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IssuerMapper {
    IssuerDto toIssuerDto(Issuer issuer);
}

package com.lazar.andric.homework.tender;

import com.lazar.andric.homework.issuer.IssuerDto;
import lombok.Data;

@Data
public class TenderDto {

    private Long id;

    private String description;

    private IssuerDto issuer;
}

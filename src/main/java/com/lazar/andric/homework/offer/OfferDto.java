package com.lazar.andric.homework.offer;

import com.lazar.andric.homework.validators.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {

    @Null(groups = OnCreate.class)
    private Long id;
    @NotNull
    private double amount;
    @AssertFalse(groups = OnCreate.class)
    private boolean accepted;
}

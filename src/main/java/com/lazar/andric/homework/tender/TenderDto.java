package com.lazar.andric.homework.tender;

import com.lazar.andric.homework.validators.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenderDto {

    @Null(groups = OnCreate.class)
    private Long id;
    @NotNull
    private String description;
}

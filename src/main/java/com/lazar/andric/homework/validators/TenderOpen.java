package com.lazar.andric.homework.validators;

import com.lazar.andric.homework.tender.Tender;
import com.lazar.andric.homework.tender.TenderRepository;
import com.lazar.andric.homework.util.exceptions.EntityNotFoundException;
import com.lazar.andric.homework.util.exceptions.ExceptionMessageFormatter;
import lombok.RequiredArgsConstructor;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Optional;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(PARAMETER)
@Retention(RUNTIME)
@Constraint(validatedBy = TenderOpenValidator.class)
@Documented
public @interface TenderOpen {
    String message() default "Tender not open for new offers or does not exist";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

@RequiredArgsConstructor
class TenderOpenValidator implements ConstraintValidator<TenderOpen, Long> {

    private final TenderRepository tenderRepository;

    @Override
    public void initialize(TenderOpen constraintAnnotation) {

    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        Optional<Tender> tender = tenderRepository.findById(value);
        if (tender.isPresent()) {
            return !tender.get().isClosedForOffers();
        }
        return false;
    }
}

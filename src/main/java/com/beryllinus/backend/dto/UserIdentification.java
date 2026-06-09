package com.beryllinus.backend.dto;

import com.beryllinus.backend.enumuration.IdentificationType;

/**
 * @param identificationType:  NIC
 * @param identificationDocNo: 123456789V
 * @param issuingCountryCode:  LK Locale.getISOCountries();
 */
public record UserIdentification(
        IdentificationType identificationType,
        String identificationDocNo,
        String issuingCountryCode
) {}

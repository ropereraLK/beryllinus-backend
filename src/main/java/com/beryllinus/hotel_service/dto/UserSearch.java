package com.beryllinus.hotel_service.dto;

import com.beryllinus.hotel_service.enumuration.PersonSearchType;

public record UserSearch(
        PersonSearchType searchType,
        String searchValue
) {}

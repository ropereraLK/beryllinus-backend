package com.beryllinus.backend.dto;

import com.beryllinus.backend.enumuration.PersonSearchType;

public record UserSearch(
        PersonSearchType searchType,
        String searchValue
) {}

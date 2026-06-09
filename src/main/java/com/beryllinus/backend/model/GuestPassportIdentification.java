package com.beryllinus.backend.model;

import java.time.LocalDate;
import lombok.Data;

@Data
public class GuestPassportIdentification extends GuestIdentification{
    private LocalDate expDate;
}

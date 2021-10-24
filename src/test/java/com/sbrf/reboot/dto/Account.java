package com.sbrf.reboot.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
public class Account {
    private long id;
    private LocalDate createDate;
    private BigDecimal balance;
}
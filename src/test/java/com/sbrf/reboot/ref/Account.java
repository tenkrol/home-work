package com.sbrf.reboot.ref;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDate;
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Account {
    private long id;
    private final String number;
    @Accessors(chain = true)
    private long clientId;
    private LocalDate createDate;
    private BigDecimal balance;
    private String currency;
}
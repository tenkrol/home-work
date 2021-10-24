package com.sbrf.reboot.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
public class Account {


    private final String number;
    @Accessors(chain = true)
    private long clientId;
}
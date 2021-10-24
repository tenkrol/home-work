package com.sbrf.reboot.atm.cassettes;

import com.sbrf.reboot.model.Banknote;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;

@Data
public class Cassette<T extends Banknote> {
    @NonNull
    private ArrayList<T> banknotes;

    public int getCountBanknotes() {
        return banknotes.size();
    }
}
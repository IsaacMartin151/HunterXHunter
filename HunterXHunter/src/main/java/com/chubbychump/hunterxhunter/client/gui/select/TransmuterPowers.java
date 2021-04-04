package com.chubbychump.hunterxhunter.client.gui.select;

import com.chubbychump.hunterxhunter.client.gui.Power;

public enum TransmuterPowers implements Power {
    POWER_ONE("Enchant"),
    POWER_TWO("Solidify"),
    POWER_THREE("Dash"),
    POWER_FOUR("Electrocute");

    public final String string;

    TransmuterPowers(final String str) {
        this.string = str;
    }

    @Override
    public String getName() {
        return string;
    }
}
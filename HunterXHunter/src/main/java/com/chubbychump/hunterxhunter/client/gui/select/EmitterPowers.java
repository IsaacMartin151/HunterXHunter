package com.chubbychump.hunterxhunter.client.gui.select;

import com.chubbychump.hunterxhunter.client.gui.Power;

public enum EmitterPowers implements Power {
    POWER_ONE("projectile_one"),
    POWER_TWO("projectile_two"),
    POWER_THREE("projectile_three"),
    POWER_FOUR("projectile_four");

    public final String string;

    EmitterPowers(final String str) {
        this.string = str;
    }

    @Override
    public String getName() {
        return string;
    }
}
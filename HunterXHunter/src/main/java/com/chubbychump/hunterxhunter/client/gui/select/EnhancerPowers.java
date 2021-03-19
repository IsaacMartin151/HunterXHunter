package com.chubbychump.hunterxhunter.client.gui.select;

import com.chubbychump.hunterxhunter.client.gui.Power;

public enum EnhancerPowers implements Power {
    POWER_ONE("jump_one"),
    POWER_TWO("jump_two"),
    POWER_THREE("regen"),
    POWER_FOUR("power_four");

    public final String string;

    EnhancerPowers(final String str) {
        this.string = str;
    }

    @Override
    public String getName() {
        return string;
    }
}
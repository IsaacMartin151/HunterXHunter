package com.chubbychump.hunterxhunter.client.gui.select;

import com.chubbychump.hunterxhunter.client.gui.Power;

public enum EmitterPowers implements Power {
    POWER_ONE("Slowness"),
    POWER_TWO("Poison"),
    POWER_THREE("Weakness"),
    POWER_FOUR("Swiftness"),
    POWER_FIVE("Regeneration"),
    POWER_SIX("Strength"),
    POWER_SEVEN("Leaping"),
    POWER_EIGHT("Explosive");

    public final String string;

    EmitterPowers(final String str) {
        this.string = str;
    }

    @Override
    public String getName() {
        return string;
    }
}
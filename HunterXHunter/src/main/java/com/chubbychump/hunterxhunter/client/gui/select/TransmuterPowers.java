package com.chubbychump.hunterxhunter.client.gui.select;

import com.chubbychump.hunterxhunter.client.gui.Power;

public enum TransmuterPowers implements Power {
    POWER_ONE("spectate_player"),
    POWER_TWO("teleport"),
    POWER_THREE("damage_levitation"),
    POWER_FOUR("damage_");

    public final String string;

    TransmuterPowers(final String str) {
        this.string = str;
    }

    @Override
    public String getName() {
        return string;
    }
}
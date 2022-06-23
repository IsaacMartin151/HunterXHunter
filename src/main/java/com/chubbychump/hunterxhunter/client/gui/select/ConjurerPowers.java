package com.chubbychump.hunterxhunter.client.gui.select;

import com.chubbychump.hunterxhunter.client.gui.Power;

public enum ConjurerPowers implements Power {
    POWER_ONE("formation_one"),
    POWER_TWO("formation_two"),
    POWER_THREE("iterate_blocktype"),
    POWER_FOUR("toggle_mount");

    public final String string;

    ConjurerPowers(final String str) {
        this.string = str;
    }

    @Override
    public String getName() {
        return string;
    }
}
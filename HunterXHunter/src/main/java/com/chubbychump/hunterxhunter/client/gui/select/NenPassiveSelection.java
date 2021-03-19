package com.chubbychump.hunterxhunter.client.gui.select;

import com.chubbychump.hunterxhunter.client.gui.Power;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.StringNBT;

public enum NenPassiveSelection implements Power {
    POWER_ONE("power_one"),
    POWER_TWO("power_two"),
    POWER_THREE("power_three"),
    POWER_FOUR("power_four"),
    POWER_FIVE("power_five");

    public final String string;

    NenPassiveSelection(final String str) {
        this.string = str;
    }

    @Override
    public String getName() {
        return string;
    }
}
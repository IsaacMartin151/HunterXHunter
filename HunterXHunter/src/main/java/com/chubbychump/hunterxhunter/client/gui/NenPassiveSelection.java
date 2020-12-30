package com.chubbychump.hunterxhunter.client.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.StringNBT;

public enum NenPassiveSelection implements IToolMode {

    POWER_ONE("hunterxhunter.power_one"),
    POWER_TWO("hunterxhunter.power_two"),
    POWER_THREE("hunterxhunter.power_three"),
    POWER_FOUR("hunterxhunter.power_four");

    public final String string;

    NenPassiveSelection(final String str) {
        this.string = str;
    }

    @Override
    public void setMode(int i) {
        //if (stack != null) {
        //    stack.setTagInfo( "mode", StringNBT.valueOf( name() ) );
        //}
    }

    @Override
    public String getName() {
        return string;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
